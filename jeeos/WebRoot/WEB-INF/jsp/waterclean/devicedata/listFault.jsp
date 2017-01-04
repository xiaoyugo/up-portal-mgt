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
					<div class="table-responsive">
						<table id="device-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
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
</body>
</html>