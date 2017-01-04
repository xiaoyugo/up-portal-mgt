<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" uri="/mytags"%>
<c:set var="ctx" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />
</head>
<body>
	<div class="row-fluid ">
		<div class="span12">
			<div class="widget-box" style="margin: 0;border-bottom: 0;">
				<div class="widget-header widget-header-blue widget-header-flat">
					<h3 class="lighter block green">请填写以下信息</h3>
				</div>
				<div class="widget-body"
					style="background-color: transparent;border: 0;">
					<div class="widget-main">
						<div class="row-fluid position-relative">
							<form class="form-horizontal" id="validation-form" method="post">
								<input type="hidden" name="roleName" value="${roleName}" /> <input
									type="hidden" name="funcName" value="${funcName}" />
								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="txtRoleName"><my:i18n zhText="角色名称"
											enText="Role Name" />：</label>
									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input type="text" name="txtRoleName" id="txtRoleName"
												readonly="readonly"
												value="<my:i18n zhText='${roleName}' enText='${roleName}'/>"
												class="col-xs-12 col-sm-6" />
										</div>
									</div>
								</div>
								<div class="space-2"></div>
								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="txtFuncName"><my:i18n zhText="菜单名称"
											enText="Menu Name" />：</label>
									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input type="text" name="txtFuncName" id="txtFuncName"
												readonly="readonly"
												value="<my:i18n zhText='${funcName}' enText='${funcName}'/>"
												class="col-xs-12 col-sm-6" />
										</div>
									</div>
								</div>
								<div class="space-2"></div>
								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="buttonName"><my:i18n zhText="按钮名称"
											enText="Button Name" />：</label>
									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input type="text" name="buttonName" id="buttonName"
												class="col-xs-12 col-sm-6" />
										</div>
									</div>
								</div>
								<div class="hr hr-dotted"></div>
								<div class="row-fluid center">
									<input type="submit" class="btn btn-sm btn-primary" value="保存"
										id="submitBtn" /> &nbsp; <input type="reset"
										class="btn btn-sm btn-primary" value="重置" />
								</div>
							</form>
						</div>
					</div>
					<!-- /widget-main -->
				</div>
				<!-- /widget-body -->
			</div>
		</div>
	</div>
	<!-- page specific plugin scripts -->

	<script src="${ctx }/js/jquery.form.js"></script>
	<script src="${ctx }/js/jquery.validate.min.js"></script>
	<script src="${ctx }/js/select2.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	<script type="text/javascript">
		var roleName = $(":input[name='roleName']").val();
		var funcName = $(":input[name='funcName']").val();
		var api = frameElement.api, W = api.opener;
		var tolisturl = "${ctx}/system/button_list.do?roleName=" + roleName + "&funcName=" + funcName;

		jQuery(function($) {
			$(".select2").css('width', '300px').select2({
				allowClear : true
			}).on('change', function() {
				$(this).closest('form').validate().element($(this));
			});

			$("#submitBtn").on('click', function() {
				if (!$('#validation-form').valid()) {
					return false;
				}
			});

			$("#validation-form").validate({
				errorElement : 'div',
				errorClass : 'help-block',
				focusInvalid : false,
				rules : {
					buttonName : {
						required : true,
						maxlength : 50
					}
				},
				highlight : function(e) {
					$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
				},
				success : function(e) {
					$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
					$(e).remove();
				},
				errorPlacement : function(error, element) {
					if (element.is(':checkbox') || element.is(':radio')) {
						var controls = element.closest('div[class*="col-"]');
						if (controls.find(':checkbox,:radio').length > 1)
							controls.append(error);
						else
							error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
					} else
						error.insertAfter(element.parent());
				},
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						type : 'POST',
						url : "${ctx}/system/button_save.do",
						dataType : 'json',
						error : function() {
							alert('操作失败，如多次重复出现，请联系管理员!');
							return;
						},
						success : function(data) {
							console.log(data);
							alert(data.msg);
							api.reload(W, tolisturl);
							api.close();
						}
					});
				},
				invalidHandler : function(form) {
				}
			});
		});
	</script>
</body>
</html>