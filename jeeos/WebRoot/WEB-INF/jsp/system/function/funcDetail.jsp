<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<%@ taglib prefix="my" uri="/mytags"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />
<style  type="text/css">
th
{
text-align:right;
}
</style>
<script src="${ctx }/js/jquery.form.js"></script>
<script type="text/javascript">

	$(function() {
	
		$("#submitBtn").click(function() {
		
	if ($("#chFuncName").val() == "") {
				$.gritter.add({
					title : "",
					text : "您未填写“菜单名称”，请完善后再操作！",
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
			$("#menuForm").ajaxSubmit({
				url : "${ctx}/system/func_save.do",
				type : 'POST',
				dataType : 'json',
				error : function() {
					$.gritter.add({
						title : "",
						text : "系统异常，操作失败！",
						sticky : false,
						before_open : function() {
							if ($('.gritter-item-wrapper').length >= 1) {
								return false;
							}
						},
						class_name : 'gritter-error '
					});
					return;
				},

				success : function(data) {
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
					parent.refreshTree('${id }', $("#chFuncName").val());
					//设置部分时间暂停方便用户看清提示消息
					setTimeout("window.location.reload()", 1000);
				}
			});
		});
	});
</script>
</head>
<body id="frameBody">
	<div class="page-content" style="padding-top: 0;">
		<div class="row">
			<div class="col-sm-12">
				<div class="tabbable" id="uldiv">
					<ul class="nav nav-tabs" id="myTab">
						<li class="active" id="current"><a
							href="${ctx }/system/func_update.do?id=${id }">详情 </a>
						</li>

						<li><a href="${ctx }/system/func_list.do?id=${id }">
								列表 </a>
						</li>
						<li><a href="${ctx }/system/func_add.do?id=${id }">
								新增 </a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<form id="menuForm"  method="post">
			<input type="hidden" name="checkId" id="checkId" value=""> <input
				type="hidden" name="id" id="id" value="${id }">
			<table class="table table-striped table-bordered table-hover" style="border: 0;">
				<c:if test="${!empty chFunc.chFuncName}">
					<tr>
						<th width="20%"><my:i18n zhText="上级菜单" enText="Parent Menu" />：</th>
						<td ><my:i18n zhText="${chFunc.chFuncName}"
								enText="${chFunc.chFuncName}" /></td>
					</tr>
				</c:if>
				<tr>
					<th width="20%"><font color="red">*</font>
					<my:i18n zhText="菜单名称" enText="Chinese Menu Name" />：</th>
					<td><input type="text" id="chFuncName" name="chFuncName"
						value="${chFuncName }" style="width: 100%">
					</td>
				</tr>
				<tr>
					<th width="20%"><my:i18n zhText="路径" enText="Menu Path" />：</th>
					<td><input type="text" name="chFuncPath"
						value="${chFuncPath }" style="width: 100%">
					</td>
				</tr>
				<tr>
					<th width="20%"><my:i18n zhText="备注" enText="Remark" />：</th>
					<td><textarea style="width: 100%;height: 60px"
							name="chFuncMemo">${chFuncMemo }</textarea>
					</td>
				</tr>
				<tr></tr>
				<tr>
			<td colspan="2" class="ftablebutton">
			<input type="button" class="btn btn-sm btn-primary" value="保存"  id="submitBtn" /> &nbsp; 
								  			<input type="reset" class="btn btn-sm btn-primary" value="重置" />
			</td>
			</tr>
			</table>
		</form>
	</div>
</body>
</html>