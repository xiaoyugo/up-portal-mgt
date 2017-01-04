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
		<form action="${ctx}/waterclean/agent_list.do" method="post" id="agentForm">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
				<table class="table table-bordered" style="width: 100%;margin-bottom: 0;">
			<tr style="text-align: center;vertical-align: middle; ">
				<td style="text-align: left;vertical-align: top; ">代理商名字/设备：<input type="text" name="queryterm" id="queryterm" value="${queryterm}" style="width:200px"/>
					&nbsp;&nbsp;&nbsp;
					<button id="submitBtn" type="button" class="btn-primary btn-mini"
						title="Search">
						<i class="icon-search icon-white">&nbsp;&nbsp;查询</i>
					</button>
					 </td>
					 <td style="text-align: right;vertical-align: top; ">
					<button class="btn-primary btn-mini" id="addnewButton" type="button" 
						<my:noAuth  buttonCode="addAgent"></my:noAuth>>新增代理商</button>
					 </td>
			</tr>
		</table>
					<div class="table-responsive">
						<table id="device-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<!-- <th class="center"><label> <input type="checkbox" class="ace" /> <span class="lbl"></span> </label></th> -->
									<th>代理商名字</th>
									<th>电话</th>
									<th>所在地</th>
									<th>关联设备ID</th>
									<th>代理商等级</th>								
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${page.list}" var="agent" varStatus="status">
									<tr >
										<td>${agent.agentname}</td>
										<td >${agent.phone}</td>
										<td >${agent.adress}</td>
										<td >${agent.sn}</td>
										<td >${agent.agentclass}</td>
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
				document.getElementById("agentForm").submit();			
			});
	$("#addnewButton").click(function(){
				//window.location.href=addnewurl;
				//var roleId=document.getElementById("roleID").value;
				showAdd();
			});
		function showAdd(){
			$.dialog({id:'id',title:'新增代理商',content: 'url:${ctx }/waterclean/agent_add.do?', width: '450px',height: '600px' });
		}
	</script>
</body>
</html>