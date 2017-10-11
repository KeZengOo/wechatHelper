package com.nuoxin.virtual.rep.api.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel注解，用于生成excel表格
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Excel {

    //列名
    String name() default "";

    //宽度
    int width() default 20;

    //忽略该字段
    boolean skip() default false;

}
