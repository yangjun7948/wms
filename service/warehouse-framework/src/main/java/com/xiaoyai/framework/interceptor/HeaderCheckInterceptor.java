package com.xiaoyai.framework.interceptor;

import com.alibaba.fastjson2.JSON;
import com.xiaoyai.common.annotation.CheckHeaderAuth;
import com.xiaoyai.common.annotation.RepeatSubmit;
import com.xiaoyai.common.core.domain.AjaxResult;
import com.xiaoyai.common.utils.ServletUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 请求头校验拦截器
 *
 * @author xiaoyai
 */
@Component
public abstract class HeaderCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class _clazz = handlerMethod.getMethod().getDeclaringClass();
            Method method = handlerMethod.getMethod();
            CheckHeaderAuth annotation = method.getAnnotation(CheckHeaderAuth.class);
            if (annotation == null) {
                annotation = (CheckHeaderAuth) _clazz.getAnnotation(CheckHeaderAuth.class);
            }
            StringBuilder errMsg = new StringBuilder();
            if (annotation != null && !this.isHeaderAuthAllow(request, annotation,errMsg)) {
                AjaxResult ajaxResult = AjaxResult.error(errMsg.toString());
                ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
                return false;
            }
            return true;

        }
        return true;
    }

    /**
     * 验证请求头中是否包含auth信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    public abstract boolean isHeaderAuthAllow(HttpServletRequest request, CheckHeaderAuth annotation,StringBuilder error);
}
