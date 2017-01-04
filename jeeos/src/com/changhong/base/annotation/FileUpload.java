package com.changhong.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 附件注解
 * 在action的一个method上使用，通过该method上传附件
 * @author wanghao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.METHOD})
public @interface FileUpload {
	String filePath() default "";//附件保存到磁盘上的路径
	boolean isRecordToDB() default true;//是否保存附件信信息到数据库
	//boolean filemMust() default false;//附件是否是必须的
}
