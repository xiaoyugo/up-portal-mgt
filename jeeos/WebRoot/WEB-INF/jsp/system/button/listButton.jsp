<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/mytags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>
<script type="text/javascript">
	$(function() {
		$("#roleSelect").bind('change', function() {
			addFuncContent($(this).val());
		});

		$("#funcSelect").bind('change', function() {
			var funcId = $("#funcSelect option:selected").val();
			var roleId = $("#roleSelect option:selected").val();

			if (roleId == -1) {
				alert("请选择角色!");
				return;
			}
			if (funcId == -1) {
				alert("请选择菜单!");
				return;
			}
			$("#buttonForm").submit();
		});

		//For jquery dataTable
		$('#buttonTable th input:checkbox').on('click', function() {
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox').each(function() {
				this.checked = that.checked;
				$(this).closest('tr').toggleClass('selected');
			});

		});

		$("#saveButton").on('click', function() {
			var buttonIds = "";
			$('#buttonTable td input:checkbox:checked').each(function() {
				buttonIds += "," + $(this).val();
			});
			var funcId = $("#funcSelect option:selected").val();
			var roleId = $("#roleSelect option:selected").val();

			if (roleId == -1) {
				alert("请选择角色!");
				return;
			}
			if (funcId == -1) {
				alert("请选择菜单!");
				return;
			}

			$.ajax({
				url : encodeURI("${ctx}/system/button_saveButton.do?funcId=" + funcId + "&roleId=" + roleId + "&buttonIds=" + buttonIds),
				type : 'POST',
				dataType : "json",
				error : function() {
					alert("系统异常，如多次出现请联系管理员！");
				},
				success : function(data) {
					alert("保存成功!");
					$("#buttonForm").submit();
				}
			});
		});
	});

	function addFuncContent(roleId) {
		$.ajax({
			url : encodeURI("${ctx}/system/button_funcList.do?roleId=" + roleId),
			type : 'POST',
			dataType : "json",
			error : function() {
				alert("获取角色的权限范围失败");
			},
			success : function(data) {
				$("#funcSelect").empty();
				$("#funcSelect").append("<option value='-1' selected='selected' >请选择菜单</option>");
				var content = "";
				if (data != null && data.length > 0) {
					for ( var i = 0; i < data.length; i++) {
						var func = data[i];
						content += "<option value='"+func.id+"'>" + func.chFuncName + "</option>";
					}
					$("#funcSelect").append(content);
				}
			}
		});
	}

	function addDefaultButtons() {
		var funcId = $("#funcSelect option:selected").val();
		var roleId = $("#roleSelect option:selected").val();
		
		if (roleId == -1) {
			alert("请选择角色!");
			return;
		}
		if (funcId == -1) {
			alert("请选择菜单!");
			return;
		}
		if('${haveButtonList.size()}' > 0){
			alert("默认按钮已添加，请勿重复操作！");
			return;
		}
		$.ajax({
			url : encodeURI("${ctx}/system/button_addButton.do?funcId=" + funcId),
			type:"GET",
			dataType : "json",
			error : function() {
				alert("系统异常，如多次出现请联系管理员！");
			},
			success : function(data) {
				alert(data.msg);
				$("#buttonForm").submit();
			}

		});
	}
</script>
</head>
<body>
	<div class="page-content">
		<form id="buttonForm" method="post"
			action="${ctx}/system/button_list.do">
			<div class="table-header">
				<my:i18n zhText="角色" enText="Role" />
				： <select name="roleName" id="roleSelect">
					<option value="-1" selected="selected">请选择角色</option>
					<c:forEach items="${roleList}" var="role">
						<c:choose>
							<c:when test="${roleName eq role.id}">
								<option value="${role.id}" selected="selected">${role.chRoleName}</option>
							</c:when>
							<c:otherwise>
								<option value="${role.id }">${role.chRoleName }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<my:i18n zhText="菜单" enText="Function" />
				： <select name="funcName" id="funcSelect">
					<option value="-1" selected="selected">请选择菜单</option>
					<c:if test="${empty funcList}">
						<option value="-1">此角色没有功能菜单</option>
					</c:if>
					<c:forEach items="${funcList}" var="func">
						<c:choose>
							<c:when test="${funcName eq func.id}">
								<option value="${func.id}" selected="selected">${func.chFuncName}</option>
							</c:when>
							<c:otherwise>
								<option value="${func.id }">${func.chFuncName }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
				<div class="pheadbutton"></div>
				<div class="clear"></div>
			</div>

			<div class="row-fluid">
				<div class="span10">
					<table class="table table-striped table-bordered table-hover"
						id="buttonTable">
						<thead class="">
							<tr>
								<th width="5%" style="text-align: center;"><input
									type="checkbox" name="checkId" id="checkId" value="">
								</th>
								<th width="5%"><my:i18n zhText="序号" enText="No." />
								</th>
								<th width="40%">按钮代码</th>
								<th width="40%">按钮名称</th>
							</tr>
						</thead>
						<tbody class="">
							<c:if test="${empty buttonList}">
								<tr>
									<td align="center" colspan="5"><font color="red"><my:i18n
												zhText="当前角色的菜单没有功能按钮" enText="There are no Button" /> </font>
									</td>
								</tr>
							</c:if>
							<c:forEach items="${buttonList}" var="button" varStatus="status">
								<tr id="${button.id }">
									<td align="center"><input type="checkbox" name="ids"
										value="${button.id}" id="btnId_${button.id}" /></td>
									<td align="center"><my:rowNum page="${page}"
											rowIndex="${status.index}" />
									</td>
									<td align="center">${button.chButtonCode }</td>
									<td align="center">${button.chButtonName }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div class="row-fluid">
				<div class="span6">
					<div class="dataTables_info pull-left">
						<input type="button" class="btn btn-sm btn-primary"
							value="<my:i18n zhText="保存" enText="Save"/>" id="saveButton" />
						<input type="button" class="btn btn-sm btn-primary"
							value="<my:i18n zhText="新增" enText="Add"/>" id="addButton"
							onclick="javascript:addDefaultButtons();" />
					</div>
				</div>
			</div>
		</form>
		<!-- 将已有的按钮选中 -->
		<script type="text/javascript">
			<c:forEach items="${haveButtonList}" var="haveButton">
			$("#btnId_" + '${haveButton.chButton.id}').attr('checked', true);
			</c:forEach>
		</script>
	</div>
</body>
</html>