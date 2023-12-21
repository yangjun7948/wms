package com.xiaoyai.framework.interceptor.impl;

import com.xiaoyai.common.annotation.CheckHeaderAuth;
import com.xiaoyai.framework.interceptor.HeaderCheckInterceptor;
import com.xiaoyai.framework.web.service.TokenService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * todo 校验api中的请求头是否符合要求，暂时先不实现
 * @author junyang
 * @date 2023/7/18
 */
@Component
public class HeaderAuthInterceptor extends HeaderCheckInterceptor {
    @Override
    public boolean isHeaderAuthAllow(HttpServletRequest request, CheckHeaderAuth annotation,StringBuilder err) {
        // 获取请求头信息
        String appId = request.getHeader("AppId");
        if(!TokenService.APP_ID.equals(appId)){
            err.append("APPID校验不通过");
            return false;
        }
        return true;
    }
}
