<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<%@ taglib prefix="my" uri="/mytags"%>
</head>
<body>
	<div class="page-content">
		<form action="${ctx}/foodmanage/goodsmanage_list.do" method="post" id="GoodslistForm">
			<%-- PAGE CONTENT BEGINS --%>
			<div class="row">
				<div class="col-xs-12">
				<table class="table table-bordered" style="width: 100%;margin-bottom: 0;">
			<tr style="text-align: center;vertical-align: middle; ">
				<td style="text-align: left;vertical-align: top; ">商品条码或商品名：<input type="text" name="queryitem" id="queryitem" value="${queryitem}" style="width:200px"/>
					&nbsp;&nbsp;&nbsp;
					<button id="submitBtn" type="submit" class="btn-primary btn-mini"
						title="Search">
						<i class="icon-search icon-white">&nbsp;&nbsp;查询</i>
					</button>
					 </td>
								 <td style="text-align: right;vertical-align: top; ">
					<button class="btn-primary btn-mini" id="addnewButton" type="button" 
						<my:noAuth  buttonCode="addGoods"></my:noAuth>>新增商品</button>
			    </td>	 
			</tr>
		</table>
					<div class="table-responsive">
						<table id="goods-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>商品条形码</th>
									<th>商品名称</th>
									<th>规格</th>	
									<th>单位</th>
									<th>产地</th>	
									<th>操作</th>													
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${page.list}" var="goods" varStatus="status">
									<tr >

										<td>${goods.itemno}</td>
										<td class="hidden-320">${goods.itemname}</td>
										<td>${goods.itemsize}</td>
										<td class="hidden-480">${goods.unitno}</td>
										<td class="hidden-480">${goods.productarea}</td>
										<td>
											<div
												class="action-buttons">
                                    
												<a class="green"
													href="javascript:editGoods('${goods.itemno}','${goods.itemname}','${goods.itemsize}','${goods.unitno}','${goods.productarea}');"  
													title="编辑"> <i class="icon-pencil bigger-130"></i></a>
												      <a class="red" title="删除" href="javascript:deleteGoods('${goods.itemno}')" > <i class="icon-trash bigger-130"></i></a>
											</div>
											</td>
				                    
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="pull-right">
									<my:page page="${page}" />
						</div>
					</div>
					
				</div>
				
			</div>
			
		</form>
	</div>
	<%-- /.page-content --%>
	<%-- page specific plugin scripts --%>
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>

	<%-- inline scripts related to this page --%>

	<script type="text/javascript">
		$("#submitBtn").click(function(){
				$('#currentPage').val(1);
				document.getElementById("faultForm").submit();			
			});
	$.dialog.setting.extendDrag = true;
	var deleteUrl = "${ctx}/foodmanage/goodsmanage_delete.do";
	function editGoods(itemno,itemname,itemsize,unitno,productarea){
			$.dialog({
				lock:true,
				drag: true,
				resize: true,
				min:false,
			 	max:false,
				width: '800px',
				height: '503px',
				id:'editGoods',
				title:'商品编辑',
				content:encodeURI('url:${ctx }/foodmanage/goodsmanage_update.do?&itemno='+encodeURI(itemno) +'&itemname='+encodeURI(itemname) +'&itemsize='+encodeURI(itemsize) +'&unitno='+encodeURI(unitno) +'&productarea='+encodeURI(productarea)) , 
			  
			});
			
		}
		
	$("#addnewButton").click(function(){
				//window.location.href=addnewurl;
				//var roleId=document.getElementById("roleID").value;
				showAdd();
			});
		function showAdd(){
			$.dialog({id:'id',title:'新增商品',content: 'url:${ctx }/foodmanage/goodsmanage_add.do?', width: '450px',height: '600px' });
		}
	
function deleteGoods(id) {
			$.dialog.confirm("确定要删除选中数据吗？", function() {
				$.ajax({
					url : deleteUrl + "?id=" + id,
					type : 'GET',
					error : function() {
						 $.gritter.add({
			                               title : "",
			                               text : "系统异常，系统已放弃本次操作!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
					                       },
					success : function(data) {
						//data = $.parseJSON(data);

						//不存在msg时，当作异常处理
						if (data.msg == undefined) {
						     $.gritter.add({
			                               title : "",
			                               text : "系统异常，请联系管理员！",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
							return;
						}
						//删除成功提示信息
						$.gritter.add({
						  title : "",
						  text : data.msg,
						  sticky : false,
						  before_open : function() {
							  if ($('.gritter-item-wrapper').length >= 1) {
								  return false;
							  }
						  },
						  class_name : 'gritter-success '
						});
						setTimeout("window.location.reload()", 1000);
					}
				});
			});
		}
	
	</script>
</body>
</html>