package com.xiaoyai.common.annotation;

import java.lang.annotation.*;

/**
 * @author junyang
 * @date 2023/11/27
 * 仓库权限注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WarehouseScope {
    /**
     * 仓库权限表的别名
     */
    public String warehouseAlias() default "";
}
