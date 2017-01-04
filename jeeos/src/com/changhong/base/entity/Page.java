package com.changhong.base.entity;

import java.util.List;
/**
 * 分页类
 * 
 * @author wanghao
 * 2009-05-01 晚22:43
 */
public class Page {
	
	//当前页
	private int currentPage=1;
	//前一页
	private int previousPage=0;
	//后一页
	private int nextPage=0;
	//共有？页
	private int totalPage=0;
	//共？条记录
	private int totalRecords=0;
	//一页显示？条
	private int pageSize=20;
	//是否有前一页
	private boolean hasPrevious=false;
	//是否有下一页
	private boolean hasNext=false;
	
	//排序字段、降序/升序,desc,asc
	private String orderattr="";
	private String ordertype="";
	
	public String getOrderattr() {
		return orderattr;
	}
	public void setOrderattr(String orderattr) {
		this.orderattr = orderattr;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	//分页数据List
	private List<?> list = null;
	
	/**
	 * 空构造器
	 */
	public Page(){
		
	}
	/**
	 * 输入page对象构造器，设置每页显示的记录条数
	 * @param pageSize 每页显示的条数
	 */
    public Page(int pageSize){
		setPageSize(pageSize);
	}
	/**
	 * 输出page对象构造器（带有分页数据的page对象）
	 * @param list 返回的list数据
	 * @param currentPage 当前页数
	 * @param totalRecords 所有的记录条数
	 * @param pageSize 每页显示的条数
	 */
	@SuppressWarnings("unchecked")
	public Page(List list,int currentPage,int totalRecords,int pageSize,String orderattr,String ordertype){
		
		this.list=list;
		this.currentPage=currentPage;
		this.totalRecords=totalRecords;
		this.pageSize=pageSize;
		this.orderattr=orderattr;
		this.ordertype=ordertype;
		
		setTotalPage((totalRecords + pageSize - 1) / pageSize);
		
		if(currentPage>this.getTotalPage()){
			this.setCurrentPage(getTotalPage());
		}
		
		if(currentPage < 1){
			this.setCurrentPage(1);
		}
		if(currentPage==1){
			setHasPrevious(false);
		}else{
			setHasPrevious(true);
			setPreviousPage(currentPage-1);
		}
		
		if(currentPage*pageSize<totalRecords){
			setHasNext(true);
			setNextPage(currentPage+1);
		}else{
			setHasNext(false);
		}
	}
	
	//------------------------------以下是getter/setter方法---------------------------------------
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	@SuppressWarnings("unchecked")
	public List getList() {
		return list;
	}
	@SuppressWarnings("unchecked")
	public void setList(List list) {
		this.list = list;
	}
}
