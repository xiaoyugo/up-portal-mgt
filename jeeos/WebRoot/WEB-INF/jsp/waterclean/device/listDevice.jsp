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
		<form action="${ctx}/waterclean/device_list.do" method="post" id="DeviceForm" name="DeviceForm">
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
									<th class="hidden-320">设备版本号</th>
									<th class="hidden-480"><i class="icon-time bigger-110 hidden-480"></i>激活时间</th>
									<th >设备在线状态</th>
									<th class="hidden-480">设备故障状态</th>
									<th class="hidden-480">关联用户</th>
									<th>滤芯设置</th>
									<th class="hidden-480">关联代理商</th>									
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${page.list}" var="device" varStatus="status">
									<tr >
										<%-- <td class="center"><label> <input type="checkbox" 
												class="ace" id="ids" name="ids" value="${user.id}" /> <span
												class="lbl"></span> <input type="hidden" id="roleID"
												name="roleID" value="${user.chRole.id}" /> </label></td> --%>

										<td><div
												class="action-buttons">
												<a class="blue"
													href="javascript:clickSn('${device.sn}');">${device.sn}</a>
											</div></td>
										<td class="hidden-320">${device.software}</td>
										<td class="hidden-480">${device.createtime}</td>
										<td>
										<c:choose>
												<c:when test="${device.onlinestate=='0'}">
													<span class="label label-sm badge-grey">离线
													</span>
												</c:when>
												<c:otherwise>
													<span class="label label-sm badge-success">在线
													</span>
												</c:otherwise>
											</c:choose></td>
										<td class="hidden-480">
											<c:choose>
												<c:when test="${device.faultstate=='0'}">
													<span class="label label-sm badge-success">正常
													</span>
												</c:when>
												<c:otherwise>
													<span class="label label-sm label-danger">故障
													</span>
												</c:otherwise>
											</c:choose></td>
											<td class="hidden-480">
											<div
												class="action-buttons">
												<a class="blue"
													href="javascript:clickUser('${device.username}');">${device.username}</a>
											</div></td>
										   <td>
											<div
												class="action-buttons">
												<a class="blue"
													href="javascript:editUser('${device.sn}');">复位</a>
											</div>
											</td> 
											<td class="hidden-480"><div
												class="action-buttons">
												<a class="blue"
													href="javascript:clickAgent('${device.agentname}');">${device.agentname}</a>
											</div></td>
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
				document.getElementById("DeviceForm").submit();			
			});
	function clickUser(username){
			$.dialog({
				lock:true,
				drag: true,
				resize: true,
				min:false,
			 	max:false,
				width: '800px',
				height: '503px',
				id:'clickUser',
				title:'关联用户',
				content: 'url:${ctx }/waterclean/device_user.do?username='+username
			});
		}
	function clickSn(sn){
			$.dialog({
				lock:true,
				drag: true,
				resize: true,
				min:false,
			 	max:false,
				width: '1000px',
				height: '503px',
				id:'clickSn',
				title:'设备数据',
				content: 'url:${ctx }/waterclean/device_devicesn.do?selectesn='+sn
			});
		}
		function clickAgent(agentname){
			$.dialog({
				lock:true,
				drag: true,
				resize: true,
				min:false,
			 	max:false,
				width: '1000px',
				height: '503px',
				id:'clickSn',
				title:'关联代理商',
				content: encodeURI('url:${ctx }/waterclean/device_agent.do?selecteagent='+encodeURI(agentname))
			});
		}
	</script>
</body>
</html>