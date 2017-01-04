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
		<form action="${ctx}/waterclean/fault_list.do" method="post" id="faultForm">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
				<table class="table table-bordered" style="width: 100%;margin-bottom: 0;">
			<tr style="text-align: center;vertical-align: middle; ">
				<td style="text-align: left;vertical-align: top; ">故障码：<input type="text" name="selectfault" id="selectfault" value="${selectfault}" style="width:200px"/>
					&nbsp;&nbsp;&nbsp;
					<button id="submitBtn" type="button" class="btn-primary btn-mini"
						title="Search">
						<i class="icon-search icon-white">&nbsp;&nbsp;查询</i>
					</button>
					 </td>
					 <td style="text-align: right;vertical-align: top; ">
					<button class="btn-primary btn-mini" id="addnewButton" type="button" 
						<my:noAuth  buttonCode="addFault"></my:noAuth>>新增故障</button>
					<button class="btn-primary btn-mini" id="deleteButton" type="button"
						<my:noAuth  buttonCode="deleteFault"></my:noAuth>>删除</button>
					 </td>
			</tr>
		</table>
					<div class="table-responsive">
						<table id="device-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
								    <th class="center"><label> <input type="checkbox" class="ace" /> <span class="lbl"></span> </label></th>
									<th>故障码</th>
									<th>故障值</th>
									<th>故障描述</th>	
									<th>故障频率</th>
									<th>常见机型</th>																
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${page.list}" var="fault" varStatus="status">
									<tr >
									<td class="center"><label> <input type="checkbox" 
												class="ace" id="ids" name="ids" value="${fault.id}" /> <span
												class="lbl"></span></label></td>
										<td>0x${fault.faultcode}</td>
										<td >0x${fault.faultvalue}</td>
										<td>${fault.faultdescribe}</td>
										<td ><fmt:formatNumber type="number" pattern="0.00%" value="${fault.frequency}"/></td>
										<td >${fault.commodel}</td>										
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="pull-right">
									<my:page page="${page}" />
						</div>
					</div>
					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</form>
	</div>
	<!-- /.page-content -->
	<!-- page specific plugin scripts -->
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	$("#submitBtn").click(function(){
				$('#currentPage').val(1);
				document.getElementById("faultForm").submit();			
			});
	$.dialog.setting.extendDrag = true;
	var deleteUrl = "${ctx}/waterclean/fault_delete.do";
	
	$("#addnewButton").click(function(){
				//window.location.href=addnewurl;
				//var roleId=document.getElementById("roleID").value;
				showAdd();
			});
		function showAdd(){
			$.dialog({id:'id',title:'新增故障',content: 'url:${ctx }/waterclean/fault_add.do?', width: '450px',height: '600px' });
		}
		
	$("#deleteButton").click(function(){
				//window.location.href=addnewurl;
				//var roleId=document.getElementById("roleID").value;
				ajaxDelete();
			});
	function ajaxDelete(){
				var ids="";
				$('input[type="checkbox"][name="ids"]:checked').each(
					function() {
						ids +=","+$(this).val();
					}
				);
				
		if (ids == "") {
				$.gritter.add({
					title : "",
					text : "请选择需要删除的数据!",
					sticky : false,
					before_open : function() {
						if ($('.gritter-item-wrapper').length >= 1) {
							return false;
						}
					},
					class_name : 'gritter-error '
				});
				return;
			}
			if (ids.indexOf(",") == 0) {
				ids = ids.substring(1);
			}
			$.dialog.confirm("确定要删除选中数据吗？", function() {
				$.ajax({
					url : deleteUrl + "?ids=" + ids,
					type : 'GET',
					error : function() {
						$.gritter.add({
						  title : "",
						  text : "系统异常，请联系管理员！",
						  sticky : false,
						  before_open : function() {
							  if ($('.gritter-item-wrapper').length >= 1) {
								  return false;
							  }
						  },
						  class_name : 'gritter-success '
						});
							return;
					},
					success : function(data) {
						//data = $.parseJSON(data);

						//不存在msg时，当作异常处理
						if (data.msg == undefined) {
						$.gritter.add({
						  title : "",
						  text : "系统异常，请联系管理员！",
						  sticky : false,
						  before_open : function() {
							  if ($('.gritter-item-wrapper').length >= 1) {
								  return false;
							  }
						  },
						  class_name : 'gritter-success '
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
						setTimeout("window.location.reload()", 700);
					}
				});
			});
		}
		jQuery(function($) {
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});
		});
	</script>
</body>
</html>