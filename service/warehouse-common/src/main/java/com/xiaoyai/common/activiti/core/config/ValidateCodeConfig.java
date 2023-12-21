package com.xiaoyai.common.activiti.core.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 登录验证码校验配置
 * @author dingj
 * @date 2020/1/13 10:22
 */
@Component
@Data
public class ValidateCodeConfig {

    /**
     * 最低无验证码校验次数，默认值为三次
     */
    @Value("${com.zykj.auth.codeValidateCount:3}")
    private int codeValidateCount;

    /**
     * 验证码输入错误时，redis缓存时限，单位时秒，默认300秒/5分钟
     */
    @Value("${com.zykj.auth.codeValidateFailUntilTime:300}")
    private int codeValidateFailUntilTime;

}
