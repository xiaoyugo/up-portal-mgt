package com.changhong.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 菜单注解
 * 在action的菜单method上加上该注解，表示这个method是个菜单
 * 用于控制用户的访问权限
 * @author wanghao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.METHOD})
public @interface Menu {

}
