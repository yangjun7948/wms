package com.xiaoyai.common.activiti.core.annotation;

import java.lang.annotation.*;

/**
 * @author xieyu
 * @date 2021-03-30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tablelixv {
    String value();
}
