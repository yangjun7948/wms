package com.xiaoyai.framework.aspectj;

import com.xiaoyai.common.annotation.DataScope;
import com.xiaoyai.common.annotation.WarehouseScope;
import com.xiaoyai.common.core.domain.BaseEntity;
import com.xiaoyai.common.core.domain.entity.SysRole;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.common.core.domain.model.LoginUser;
import com.xiaoyai.common.core.text.Convert;
import com.xiaoyai.common.utils.SecurityUtils;
import com.xiaoyai.common.utils.StringUtils;
import com.xiaoyai.framework.security.context.PermissionContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 仓库过滤处理
 *
 * @author xiaoyai
 */
@Aspect
@Component
public class WarehouseScopeAspect {


    /**
     * 数据权限过滤关键字
     */
    public static final String WAREHOUSE_SCOPE = "warehouseScope";

    @Before("@annotation(controllerWarehouseScope)")
    public void doBefore(JoinPoint point, WarehouseScope controllerWarehouseScope) throws Throwable {
        clearDataScope(point);
        handleDataScope(point, controllerWarehouseScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, WarehouseScope controllerWarehouseScope) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {

                dataScopeFilter(joinPoint, currentUser, controllerWarehouseScope.warehouseAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint      切点
     * @param user           用户
     * @param warehouseAlias 部门别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String warehouseAlias) {
        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<String>();

        sqlString.append(StringUtils.format(" OR {}.warehouse_id IN ( SELECT warehouse_id FROM warehouse_manager WHERE user_id = {} ) ",
                warehouseAlias, user.getUserId()));

        if (StringUtils.isNotBlank(sqlString.toString())) {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(WAREHOUSE_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(WAREHOUSE_SCOPE, "");
            }
        }

    }
}
