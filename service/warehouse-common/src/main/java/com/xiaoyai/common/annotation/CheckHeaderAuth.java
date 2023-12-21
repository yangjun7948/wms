package com.xiaoyai.common.annotation;

import java.lang.annotation.*;

/**
 * @author junyang
 * @date 2023/7/18
 */
@Inherited
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckHeaderAuth {
}
