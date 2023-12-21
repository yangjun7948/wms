package com.xiaoyai.common.activiti.api.config;

import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson2.JSON;
import com.xiaoyai.common.activiti.core.annotation.NoAdvice;
import com.xiaoyai.common.activiti.core.constant.CommonConstants;
import com.xiaoyai.common.activiti.core.util.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回体增强
 * 当controller被RestController注解 或方法被ResponseBody注解时
 * 将返回结果用 com.zykj.common.core.util.R 对象包裹
 */
@ControllerAdvice
public class RespBodyAdvice implements ResponseBodyAdvice {

    /**
     * 过滤请求返回控制器
     * 只有当controller被RestController注解 或方法被ResponseBody注解时
     * 才进行返回结果增强
     *
     * @param methodParameter 请求的controller中的method
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        String packageName = methodParameter.getDeclaringClass().getName();
        if (!StrUtil.startWith(packageName, "com.xiaoyai.activiti")) {
            return false;
        }
        // 如果返回结果是void，则认为是在下载文件，也不进行增强
        if ("void".equals(methodParameter.getMethod().getReturnType().toString()))
            return false;
        RestController restController = methodParameter.getDeclaringClass().getAnnotation(RestController.class);
        ResponseBody responseBody = methodParameter.getMethodAnnotation(ResponseBody.class);
        //不需要增强
        NoAdvice noAdvice = methodParameter.getMethodAnnotation(NoAdvice.class);
        if (noAdvice != null) {
            return false;
        }
        if (restController == null && responseBody == null) {
            return false;
        }
        return true;
    }

    /**
     * 当supports返回true时执行
     * 当controller返回R时不变，直接返回R
     * 当返回结果是String时，将String构造R，再将R转化成json返回（防止controller抛出异常）
     * 当返回结果是其他Object子类时，构造成R返回
     *
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof R) {
            return o;
        }
        R result = new R();
        result.setData(o);
        result.setCode(CommonConstants.SUCCESS);
        if (o instanceof String) {
            serverHttpResponse.getHeaders().set("content-type", "application/json;charset=UTF-8");
            return JSON.toJSONString(result);
        }
        return result;
    }

}
