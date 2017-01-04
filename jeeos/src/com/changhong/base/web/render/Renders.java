package com.changhong.base.web.render;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;

import com.changhong.base.utils.LocaleUtils;
import com.changhong.base.utils.bean.Objects;
import com.changhong.base.utils.string.StringEncodeUtils;
import com.changhong.base.web.Servlets;

import flexjson.JSONSerializer;
/**
 * 渲染器
 * 渲染text、html、json、excel
 * @author wanghao
 */
public class Renders {

	private static final Log log = LogFactory.getLog(Renders.class);

	// 保存成功/失败后，返回的json提示信息
	public static final JsonResult SAVE_SUCCESS = new EasyUiResult(
			LocaleUtils.getLocaleText("保存成功", "Save Success"));
	public static final JsonResult SAVE_FAILURE = new EasyUiResult(
			LocaleUtils.getLocaleText("保存失败", "Save Failure"));
	// 上传成功后，返回的json提示信息
	public static final JsonResult UPLOAD_SUCCESS = new EasyUiResult(
			LocaleUtils.getLocaleText("上传成功", "Upload Success"));
	public static final JsonResult UPLOAD_FAILURE = new EasyUiResult(
			LocaleUtils.getLocaleText("上传失败", "Upload Failure"));
	// 修改成功后，返回的json提示信息
	public static final JsonResult UPDATE_SUCCESS = new EasyUiResult(
			LocaleUtils.getLocaleText("修改成功", "Update Success"));
	// 修改成功后，返回的json提示信息
	public static final JsonResult UPDATE_SUCCESS_JSON = new JsonResult(
			LocaleUtils.getLocaleText("修改成功", "Update Success"));
	// 删除成功后，返回的json提示信息
	public static final JsonResult DELETE_SUCCESS = new JsonResult(
			LocaleUtils.getLocaleText("删除成功", "Delete Success"));
	// 删除成功后，返回的json提示信息
	public static final JsonResult DELETE_FAILURE = new JsonResult(
			LocaleUtils.getLocaleText("删除失败", "Delete Failure"));
	// 保存成功后，返回的json提示信息
	public static final JsonResult GENERATE_SUCCESS = new EasyUiResult(
			LocaleUtils.getLocaleText("生成成功", "Generate Success"));
	// 删除提示信息，返回的json提示信息
	public static final JsonResult ROLE_CHANGE = new JsonResult(
			LocaleUtils.getLocaleText("请切换对应终端类型角色再删除", "Please Change Role"));
	// 导出提示信息，返回的json提示信息
	public static final JsonResult EXPORT_SUCCESS = new JsonResult(
			LocaleUtils.getLocaleText("导出成功！", "Export Success!"));
	// 删除提示信息，返回的json提示信息
	public static final JsonResult EXPORT_FAILURE = new JsonResult(
			LocaleUtils.getLocaleText("导出失败！", "Export Failure!"));

	/**
	 * 输出html/文本
	 * 
	 * @throws Exception
	 */
	public static void renderHtml(Object content) throws Exception {
		if(content.getClass().isPrimitive() || BeanUtils.isSimpleProperty(content.getClass())){
			content = content.toString();
		//	log.info("text or html:"+content);
		}else if(content instanceof EasyUiResult){
			content = new JSONSerializer().exclude("*.class").deepSerialize(content);	
		//	log.info("save或update请求的响应,contentType需为text/html,json字符串:"+content);
		}else{
		//	throw new Exception("渲染html/文本的数据的数据格式不正确");
		}
		render("text/html", content.toString());
	}

	/**
	 * 输出JSON.
	 * 
	 * @param Object json对象.
	 * @throws Exception 
	 */
	public static void renderJson(Object content) throws Exception {
		//EasyUiResult时，contentType为text/html
		if(content instanceof EasyUiResult){
			renderHtml(content);
			return;
		}else if(Objects.isPrimitive(content.getClass())){//基本数据类型
			content = new JsonResult(content);
		}
		content = new JSONSerializer().exclude("*.class").deepSerialize(content);	
		log.info("json:"+content);
		render("application/json", content.toString());
	}
	
	/**
	 * 输出JSON.
	 * 
	 * @param Object json对象.
	 * @throws Exception 
	 */
	public static void renderJsonWithoutDeepInto(Object content) throws Exception {
		//EasyUiResult时，contentType为text/html
		if(content instanceof EasyUiResult){
			renderHtml(content);
			return;
		}else if(Objects.isPrimitive(content.getClass())){//基本数据类型
			content = new JsonResult(content);
		}
		content = new JSONSerializer().exclude("*.class").serialize(content);	
		log.info("json:"+content);
		render("application/json", content.toString());
	}
	
	/**
	 * poi渲染excel
	 * @param wb
	 * @param excelName
	 * @throws Exception
	 */
	public static void renderExcel(Workbook wb,String excelName)throws Exception{
		 HttpServletResponse response = Servlets.getResponse();
		 response.setContentType("application/x-download");
	     response.setHeader("Content-disposition","attachment;filename=\""+ StringEncodeUtils.gbkToIso88591(excelName) + "\"");
	     OutputStream os = response.getOutputStream();
		 wb.write(os);
		 os.flush();
		 os.close();
	}
	
	/**
	 * 渲染text、html、json
	 * @param contentType
	 * @param content
	 * @throws Exception
	 */
	private static void render(String contentType,final String content)throws Exception {
		
		 HttpServletResponse response = Servlets.getResponse();
		 //设置headers参数
	     response.setContentType(contentType+";charset=UTF-8");
		 //清除缓存
    	 response.setHeader("Cach-Control", "no-cache");   
         response.setHeader("Cach-Control", "no-store");   
         response.setDateHeader("Expires", 0);   
         response.setHeader("Pragma", "no-cache"); 
            
	     PrintWriter out=response.getWriter();
		 out.write(content);
		 out.flush();
		 out.close();
	}
}
