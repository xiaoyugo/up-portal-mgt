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
		<form action="${ctx}/waterclean/user_list.do" method="post" id="UserForm">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
				<table class="table table-bordered" style="width: 100%;margin-bottom: 0;">
			<tr style="text-align: center;vertical-align: middle; ">
				<td style="text-align: left;vertical-align: top; ">用户名：<input type="text" name="username" id="sn" value="${username}" style="width:200px"/>
					&nbsp;&nbsp;&nbsp;
					<button id="submitBtn" type="button" class="btn-primary btn-mini"
						title="Search">
						<i class="icon-search icon-white">&nbsp;&nbsp;查询</i>
					</button>
					 </td>
			</tr>
		</table>
					<div class="table-responsive">
						<table id="device-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<!-- <th class="center"><label> <input type="checkbox" class="ace" /> <span class="lbl"></span> </label></th> -->
									<th>用户名</th>
									<th>关联设备</th>
									<th >创建时间</th>									
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${page.list}" var="user" varStatus="status">
									<tr >
										<td>${user.username}</td>
										<td >${user.sn}</td>
										<td >${user.crtime}</td>
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
				document.getElementById("UserForm").submit();			
			});
	</script>
</body>
</html>