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
		<form action="${ctx}/waterclean/device_devicesn.do" method="post" id="DeviceDataForm">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="table-responsive">
						<table id="device-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<!-- <th class="center"><label> <input type="checkbox" class="ace" /> <span class="lbl"></span> </label></th> -->
									<th>设备号</th>
									<th>净水器型号</th>
									<th>滤芯等级</th>
									<th>滤芯满寿命（小时）</th>
									<th>滤芯剩余寿命（小时）</th>
									<th>TDS（过滤前）</th>
									<th>TDS（过滤后）</th>
									<th>故障码</th>
									<th>故障值</th>
									<th>故障时间</th>																
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${listdevdata}" var="devdata" varStatus="status">
									<tr >
										<td rowspan="4">${devdata.sn}</td>
										<td rowspan="4">${devdata.produtmodel}</td>
										<td>第一级滤芯</td>
										<td>200</td>
										<td>${devdata.level1}</td>
										<td rowspan="4">${devdata.tdsbefore}</td>
										<td rowspan="4">${devdata.tdsafter}</td>
										<td rowspan="4">0x${devdata.faultcode}</td>
										<td rowspan="4">0x${devdata.faultvalue}</td>
										<td rowspan="4">${devdata.uptime}</td>
									</tr>
									
									<tr >
										<td>第二级滤芯</td>
										<td>400</td>
										<td>${devdata.level2}</td>
									</tr>
									<tr >
										<td>第三级滤芯</td>
										<td>1200</td>
										<td>${devdata.level3}</td>
									</tr>
									<tr >
										<td>第四级滤芯</td>
										<td>500</td>
										<td>${devdata.level4}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
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