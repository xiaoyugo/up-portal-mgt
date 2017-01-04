package com.changhong.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统功能访问日志、操作日志注解
 * 用于记录service层的访问日志
 * 对service层class中的method记录操作日志时，需同时在class和method上加上该注解
 * @author wanghao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE, ElementType.METHOD})
public @interface AccessLog {
    String accessDescribe() default ""; //描述信息 
}
