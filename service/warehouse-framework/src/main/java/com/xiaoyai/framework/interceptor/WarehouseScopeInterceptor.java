package com.xiaoyai.framework.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.xiaoyai.common.core.domain.WarehouseScope;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.common.core.domain.model.LoginUser;
import com.xiaoyai.common.utils.SecurityUtils;
import com.xiaoyai.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


import javax.sql.DataSource;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * @author junyang
 * @date 2023/11/27
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class WarehouseScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {
    private final DataSource dataSource;

    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser currentUser = loginUser.getUser();
        // 如果是超级管理员，则不过滤数据
        if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {

            StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            this.sqlParser(metaObject);
            // 先判断是不是SELECT操作
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
                return invocation.proceed();
            }

            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();

            //查找参数中包含WarehouseScope类型的参数
            WarehouseScope dataScope = findDataScopeObject(parameterObject);
            if (dataScope == null) {
                return invocation.proceed();
            }

            String scopeName = dataScope.getScopeName();
            // 优先获取赋值数据

            originalSql = StringUtils.format("select * from ({}) temp_data_scope where temp_data_scope.{} in (select warehouse_id from warehouse_manager where user_id={})",
                    originalSql, scopeName, currentUser.getUserId());
            metaObject.setValue("delegate.boundSql.sql", originalSql);
        }
        return invocation.proceed();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 查找参数是否包括DataScope对象
     *
     * @param parameterObj 参数列表
     * @return DataScope
     */
    private WarehouseScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof WarehouseScope) {
            return (WarehouseScope) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof WarehouseScope) {
                    return (WarehouseScope) val;
                }
            }
        }
        return null;
    }
}
