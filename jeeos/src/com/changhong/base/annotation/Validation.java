package com.changhong.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD,ElementType.METHOD})
public @interface Validation {

	/**
	 *是否可以为null
	 * @return
	 */
	boolean notNull() default true;
	/**
	 * 默认值
	 * @return
	 */
	String defaultValue() default "";
	
	/**
	 * 是否是email
	 * @return
	 */
	boolean email() default false;
	
	/**
	 * email格式
	 * @return
	 */
	String emailPattern() default "/^***@*******$/";
	
}
