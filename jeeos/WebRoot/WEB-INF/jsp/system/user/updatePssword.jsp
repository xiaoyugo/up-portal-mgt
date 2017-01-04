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
    <div class="row-fluid ">
		<div class="span12">
			<div class="widget-box" style="margin: 0;border-bottom: 0;">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h3 class="lighter block green">请填写以下信息</h3>
					</div>
				<div class="widget-body" style="background-color: transparent;border: 0;">
					<div class="widget-main">
						<div class="row-fluid position-relative">
								<form  class="form-horizontal" id="validation-form" method="post">
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="oldPassword"><font color="red">*</font>旧密码：</label>
										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="password" name="oldPassword" id="oldPassword"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="newPassword"><font color="red">*</font>新密码:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="password" name="newPassword" id="newPassword"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="conNewPassword"><font color="red">*</font>新密码确认:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="password" name="conNewPassword" id="conNewPassword"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

								<div class="space-2"></div>
									<div class="hr hr-dotted"></div>
										<div class="row-fluid center">
											<input type="submit" class="btn btn-sm btn-primary" value="保存"  id="submitBtn" /> &nbsp; 
								  			<input type="reset" class="btn btn-sm btn-primary" value="重置" />
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
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	
	<!-- inline scripts related to this page -->

	<script type="text/javascript">
	var api = frameElement.api,W = api.opener;
			 jQuery(function($) {
				$("#submitBtn").on('click' , function(){
						if(!$('#validation-form').valid()) {
							return false;
						}
						
				});
				
				$("#validation-form").validate({
					errorElement: 'div',
					errorClass: 'help-block',
					focusInvalid: false,
					rules: {
						oldPassword: {
							required: true,
						},
						newPassword: {
							required: true,
							minlength: 3
						},
						conNewPassword: {
							required: true,
							equalTo: "#newPassword"
						},
					},
			
					messages: {
						oldPassword: {
							required:"请验证旧密码！",
						},
						newPassword: {
							required:"请输入新密码！",
							minlength: "新密码过短！"
						},
						conNewPassword: {
							required: "请确认新密码！",
							equalTo: "前后密码不一致！"
						},
					},
			
					highlight: function (e) {
						$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
					},
			
					success: function (e) {
						$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
						$(e).remove();
					},
			
					errorPlacement: function (error, element) {
						if(element.is(':checkbox') || element.is(':radio')) {
							var controls = element.closest('div[class*="col-"]');
							if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
							else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
						}else error.insertAfter(element.parent());
					},
			
		submitHandler : function(form) {
									$(form)
											.ajaxSubmit(
													{
														url : "${ctx}/system/user_updatePassword.do?",
														type : 'POST',
														dataType : "json",
														error : function() {
															$.gritter
																	.add({
																		title : "",
																		text : "修改密码失败，请联系管理员！",
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
															$.gritter
																	.add({
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
															if (data.code == "0") {
															setTimeout("api.close()", 1000);
															}
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