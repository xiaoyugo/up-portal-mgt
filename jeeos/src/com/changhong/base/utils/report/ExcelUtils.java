package com.changhong.base.utils.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;

import com.changhong.base.annotation.Validation;
import com.changhong.base.utils.bean.ReflectionUtils;
/**
 * poi 导出excel 工具类
 * @author wanghao
 */
public class ExcelUtils {

	private final static String errorMsgFormat = "第{0}行，第{1}列";
	public static List<String> errorMsgList = new ArrayList<String>();

	/**
	 * @param templeteName 如：/uploadfile/templete/xxxx.xls
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(String filePath) throws Exception{
		filePath = ServletActionContext.getServletContext().getRealPath(filePath);
		InputStream in = new FileInputStream(filePath);
		return getWorkbook(in);
	}
	
	/**
	 * 获得workbook
	 * @param file excel文件
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbook(File file) throws Exception{
		InputStream in = new FileInputStream(file);
		return getWorkbook(in);
	}
	/**
	 * @description 创建workbook
	 * @throws
	 */
    public static Workbook getWorkbook(InputStream in) throws Exception{
    	//同时适用excel2003和excel2007，它可解决poi 导出大量数据内存溢出的问题
		Workbook wb = WorkbookFactory.create(in);
		//另一种方法，区分excel2003和excel2007
		/*if(filePath.toLowerCase().endsWith(".xls")){
			wb = new HSSFWorkbook(in);
		}else if(filePath.toLowerCase().endsWith(".xlsx")){
			wb = new XSSFWorkbook(in);
		}else{
			throw new Exception("正在读取的不是excel文件.........................");
		}*/
		IOUtils.closeQuietly(in);
		return wb;
	}
    
    
    /**
	 * 读取excel文件到List
	 * 列固定，循环行
	 * @param <T> excel的列对应的java类
	 * @param sheet sheet
	 * @param startRow 开始读取的行号
	 * @param startCell 开始读取的列
	 * @param propertyNames clazz的属性名，与excel的列相同的顺序
	 * @param clazz excel每一行映射的java类
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> toBeanList(Sheet sheet,int startRow,int startCell,
			Class<? extends T> clazz,String ... propertyNames) throws Exception{
		List<T> list = new ArrayList<T>();
	
		T obj = null;
		int totalRows = sheet.getLastRowNum();
		Row row = null;
		Cell cell = null;
		for(int i=startRow;i<=totalRows;i++){//循环行
			obj = clazz.newInstance();
			row = sheet.getRow(i);
			int cellNum = 0;
			for(int j=0;j<propertyNames.length;j++){//循环列
				cellNum = j+startCell;
				//列值
				cell = row.getCell(cellNum);
				Object cellValue = getCellValue(cell);
				//
				ReflectionUtils.setFieldValue(obj, propertyNames[cellNum], cellValue);
				
				//数据校验
				Field field = ReflectionUtils.getAccessibleField(obj, propertyNames[cellNum]);
				
				//注解方式校验
				Annotation[] annotations = field.getAnnotations();
				Validation validation = null;
				if(annotations!=null && annotations.length>0){
					for(Annotation annotation : annotations){
						//非null校验
						if(annotation instanceof Validation){
							validation = (Validation) annotation;
							//非空
							if(validation.notNull() && (cellValue==null || StringUtils.isBlank(cellValue+""))){
								//设置了默认值
								if(StringUtils.isNotBlank(validation.defaultValue())){
									cellValue = validation.defaultValue();
									ReflectionUtils.setFieldValue(obj, propertyNames[cellNum], cellValue);
								}else{
									errorMsgList.add(MessageFormat.format(errorMsgFormat,i+1,j+1)+"数据不能为空");
								}
							}
							
						}
						
						//其他校验
					}
				}
			}
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 将List数据，写入到excel
	 * 列固定，循环行
	 * @param <T> 
	 * @param wb Workbook
	 * @param sheetAt sheet号
	 * @param startRow 开始写入的行号
	 * @param datas list数据
	 * @param fieldNames T的属性名，与excel的列相同的顺序
	 * @return
	 * @throws Exception
	 */
	public static <T> void toCellByList(Sheet sheet,int startRow,int startCell,
			List<T> datas,String ... fieldNames) throws Exception{
		//模板行
		Row rowTemplate = sheet.getRow(startRow);
		Object cellValue = null;
		if(datas!=null && datas.size()>0){
			for(int i=0;i<datas.size();i++){
				for(int j=0;j<fieldNames.length;j++){
					cellValue = null;
					if(StringUtils.isNotBlank(fieldNames[j])){
						cellValue = ReflectionUtils.getFieldValue(datas.get(i), fieldNames[j]);
					}
					toCellByValue(sheet, startRow+i, j, null, cellValue,rowTemplate);
				}
			}
		}
	}
	

	/**
	 * 将List数据导出excel的指定行
	 * 行固定，循环列
	 * @pparam sheetAt sheet号
	 * @pparam rowNum 指定的行号
	 * @param  startCell 开始的列
	 * @pparam datas List数据
	 * @pparam fieldNames T的属性名
	 * @pparam
	 * @throws
	 */
	public static <T> void toCellInOneRowByList(Sheet sheet,int rowNum,int startCell,
			List<T> datas,String...fieldNames)throws Exception{
		Object cellValue = null;
		//将第一个单元格的样式作为整行所有单元格的样式
		CellStyle cellStyle = sheet.getRow(rowNum).getCell(startCell).getCellStyle();
		int fieldLength = fieldNames.length;
		if(datas!=null && datas.size()>0){
			for(int i=0;i<datas.size();i++){//10
				
				for(int j=0;j<fieldNames.length;j++){//2
					cellValue = null;
					if(StringUtils.isNotBlank(fieldNames[j])){
						cellValue = ReflectionUtils.getFieldValue(datas.get(i), fieldNames[j]);
					}
					toCellByValue(sheet, rowNum, j+startCell,cellStyle,  cellValue);
				}
				startCell = (startCell+1)+(fieldLength-1);
			}
		}
	}
	
	
	/**
	 * 将List数据，写入到excel
	 * 适合以列表展现数据的情况
	 * 列固定，循环行
	 * @param <T> 
	 * @param templeteFilePath excel模板文件路径
	 * @param sheetAt sheet号
	 * @param startRow 开始写入的行号
	 * @param startCell 开始写入的列号
	 * @param datas list数据
	 * @param fieldNames T的属性名，与excel的列相同的顺序
	 * @return
	 * @throws Exception
	 */
	public static <T> void toCellByListList(Sheet sheet,int startRow,int startCell,
			List<List<T>> datas,String...fieldNames) throws Exception{
		//模板行
		Row rowTemplate = sheet.getRow(startRow);
		Object cellValue = null;
		int maxDatasSize=0;
		if(datas!=null && datas.size()>0){
			for (int i=0;i<datas.size();i++) {
				if(maxDatasSize<datas.get(i).size()){
					maxDatasSize = datas.get(i).size();
				}
				int j = 0;
				for (T t : datas.get(i)) {
					for(int k=0;k<fieldNames.length;k++){
						if(StringUtils.isNotBlank(fieldNames[k])){
							cellValue = ReflectionUtils.getFieldValue(t, fieldNames[k]);
						}
						toCellByValue(sheet,startRow+i, k+j+startCell, null,cellValue,rowTemplate);
					}
					j++;
				}
			}
		}
		refreshCellStyle(sheet, startRow, startRow+datas.size()-1, startCell, startCell+maxDatasSize, null,rowTemplate);
	}
	
	/**
	 * 用数据填充excel的cell
	 * 仅接受String和Number格式的数据，非Number的数据，需转成String类型的
	 * @param sheet
	 * @param rowNum 行号
	 * @param cellNum 列号
	 * @param value cell的值
	 * @return
	 */
	public static void toCellByValue(Sheet sheet,int rowNum,int cellNum,Object value){
		toCellByValue(sheet, rowNum, cellNum, null, value, null);
    }
	
	/**
	 * 用数据填充excel的cell
	 * 仅接受String和Number格式的数据，非Number的数据，需转成String类型的
	 * @param sheet
	 * @param cellStyle cell样式
	 * @param rowNum 行号
	 * @param cellNum 列号
	 * @param value cell的值
	 * @return
	 */
	public static void toCellByValue(Sheet sheet,int rowNum,int cellNum,CellStyle cellStyle,Object value){
		toCellByValue(sheet, rowNum, cellNum, cellStyle, value, null);
    }
	
	/**
	 * 填充单元格
	 * @throws
	 */
	private static void toCellByValue(Sheet sheet,int rowNum,int cellNum,CellStyle cellStyle,Object value,Row rowTemplate){
 	    Cell cell = getCell(sheet, rowNum, cellNum, cellStyle,rowTemplate);
 	    if(value==null || StringUtils.isBlank(value+"") || "null".equals(value+"")){
 	    	cell.setCellType(Cell.CELL_TYPE_BLANK);
 	    	cell.setCellValue("");
 	    }else{
 	    	if(value instanceof String){
	 	 	    cell.setCellType(Cell.CELL_TYPE_STRING);
	 	        cell.setCellValue(value.toString());
	 	 	}else if(value instanceof Number){
	 	 	    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	 	 	    cell.setCellValue(Double.parseDouble(value.toString()));
	 	 	}
 	    }
    }
	
	/**
	 * 用公式+数据填充excel的cell
	 * @param sheet
	 * @param rowNum 行号
	 * @param cellNum 列号
	 * @param value cell的值
	 * @return
	 */
	public static void toCellByFormula(Sheet sheet,int rowNum,int cellNum,Object value){
		toCellByFormula(sheet, rowNum, cellNum, null, value);
    }
	/**
	 * 用公式+数据填充excel的cell
	 * @param sheet
	 * @param cellStyle cell样式
	 * @param rowNum 行号
	 * @param cellNum 列号
	 * @param value cell的值
	 * @return
	 */
    public static void toCellByFormula(Sheet sheet,int rowNum,int cellNum,CellStyle cellStyle,Object value){
    	Cell cell = getCell(sheet, rowNum, cellNum, cellStyle,null);
 	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
		cell.setCellFormula(value.toString());
    }
	/**
	 * 获得cell的数值
	 * @param cell
	 * @return
	 */
	public static Object getCellValue(Cell cell) {
		if(cell==null){
			return null;
		}
		Object result = null;
		switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_NUMERIC :
	            result = cell.getNumericCellValue();
	            break;
	        case Cell.CELL_TYPE_STRING :
	            result = cell.getRichStringCellValue().getString();
	            break;
	        case Cell.CELL_TYPE_FORMULA :
	            result = cell.getCellFormula();
	            break;
	        case Cell.CELL_TYPE_BOOLEAN :
	            result = cell.getBooleanCellValue();
	            break;
	        case Cell.CELL_TYPE_ERROR :
	            result = cell.getErrorCellValue();
	            break;
	        case Cell.CELL_TYPE_BLANK :
	            result = "";
	            break;
	    }
		return result;
    }
	/**
	 * 移动行，
	 * @param startRow 开始移动的行号
	 * @param endRow  结束移动的行号
	 * @param shitRowNum 向下移动的行数
	 * @throws
	 */
	public static void shiftRows(Sheet sheet,int startRow,int endRow,int shitRowNum){
		sheet.shiftRows(startRow, endRow, shitRowNum);
	}
	/**
	 * 取得公式单元格的公式,重新设置,刷新公式值
	 * @throws
	 */
	public static void refreshCellFormula(Sheet sheet){
	    for (Row eachRow : sheet) {
			for (Cell cell : eachRow) {
			    if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
			    	cell.setCellFormula(cell.getCellFormula());
			    }
			}
		}
	}
	
	/**
	 * 
	 *刷新CellStyle
	 */
	protected static void refreshCellStyle(Sheet sheet,int startRow,int endRow,int startCell,int endCell,
			CellStyle cellStyle,Row rowTemplate){
		Row row = null;
		Cell cell = null;
		for(int i=startRow;i<endRow;i++){
			row = sheet.getRow(i);
			if(row!=null){
				for(int j=startCell;j<endCell;j++){
					cell = row.getCell(j);
					if(cell==null){
						cell = row.createCell(j);
						if(cellStyle!=null){
							cell.setCellStyle(cellStyle);
						}else{
							if(rowTemplate!=null && rowTemplate.getCell(j)!=null && rowTemplate.getCell(j).getCellStyle()!=null){
								cell.setCellStyle(rowTemplate.getCell(j).getCellStyle());
							}
						}
					}
				}
			}
		}
	}
	
    
    /**
     * 获得cell
     * @throws
     */
    private static Cell getCell(Sheet sheet,int rowNum,int cellNum,CellStyle cellStyle,Row rowTemplate){
    	Row row = sheet.getRow(rowNum);
    	if(row==null){
    		row = sheet.createRow(rowNum);
    	}
 	    Cell cell = row.getCell(cellNum);
 	    if(cell==null){
 			cell = row.createCell(cellNum);
 	    }
 	    
 	    if(cellStyle!=null){
			cell.setCellStyle(cellStyle);
		}
 	    if(rowTemplate!=null){
 	    	Cell cellTemplata = rowTemplate.getCell(cellNum);
 	    	if(cellTemplata!=null && cellTemplata.getCellStyle()!=null){
 	    		cell.setCellStyle(cellTemplata.getCellStyle());
 	    	}
 	    }
 	    return cell;
    }
}

