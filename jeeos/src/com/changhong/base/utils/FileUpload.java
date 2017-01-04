package com.changhong.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 *	JSZX
 *	@author wanghao
 *	2014-02-11
 *	文件上传辅助类
 * 
 */
public class FileUpload {
	
	private static final int BUFFER_SIZE = 16 * 1024; //上传的文件缓冲区
	/**
	 * 执行Copy File 操作
	 * @param src 源文件
	 * @param dst 新路径文件
	 */
	public static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				int i;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((i = in.read(buffer)) > 0) {
					out.write(buffer,0,i);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取文件的后缀名(.*)
	 * @param fileName 文件
	 * @return 后缀
	 */
	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos).toLowerCase();
	}
	
}

