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
		<form action="${ctx}/waterclean/devicedata_list.do" method="post" id="DeviceDataForm">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
				<table class="table table-bordered" style="width: 100%;margin-bottom: 0;">
			<tr style="text-align: center;vertical-align: middle; ">
				<td style="text-align: left;vertical-align: top; ">设备号：<input type="text" name="sn" id="sn" value="${sn}" style="width:200px"/>
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
								<c:forEach items="${page.list}" var="devicedata" varStatus="status">
									<tr >
										<td rowspan="4">${devicedata.sn}</td>
										<td rowspan="4">${devicedata.produtmodel}</td>
										<td>第一级滤芯</td>
										<td>200</td>
										<td>${devicedata.level1}</td>
										<td rowspan="4">${devicedata.tdsbefore}</td>
										<td rowspan="4">${devicedata.tdsafter}</td>
										<td rowspan="4"><div
												class="action-buttons">
												<a class="blue"
													href="javascript:clickFaultcode('${devicedata.faultcode}');">0x${devicedata.faultcode}</a>
											</div></td>
										<td rowspan="4">0x${devicedata.faultvalue}</td>
										<td rowspan="4">${devicedata.uptime}</td>
									</tr>
									
									<tr >
										<td>第二级滤芯</td>
										<td>400</td>
										<td>${devicedata.level2}</td>
									</tr>
									<tr >
										<td>第三级滤芯</td>
										<td>1200</td>
										<td>${devicedata.level3}</td>
									</tr>
									<tr >
										<td>第四级滤芯</td>
										<td>500</td>
										<td>${devicedata.level4}</td>
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
				document.getElementById("DeviceDataForm").submit();			
			});
	function clickFaultcode(faultcode){
			$.dialog({
				lock:true,
				drag: true,
				resize: true,
				min:false,
			 	max:false,
				width: '800px',
				height: '503px',
				id:'clickUser',
				title:'故障数据',
				content: 'url:${ctx }/waterclean/devicedata_fault.do?faultcode='+faultcode
			});
		}
	</script>
</body>
</html>