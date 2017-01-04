<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html >
<head >
<meta name="decorator" content="default" />
</head>
<body >
	<div class="row-fluid ">
		<div class="span12">
			<div class="widget-box" style="margin: 0;border-bottom: 0;">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h3 class="lighter block green">请填写以下信息</h3>
					</div>
				<div class="widget-body" style="background-color: transparent;border: 0;">
					<div class="widget-main">
						<div class="row-fluid position-relative">
						<form  class="form-horizontal" id="foodnoForm" method="post">
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											 for="queryitem"><font color="red">*</font>商品条码：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="itemno" id="itemno" readonly="readonly"
												 value="${id}"	class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>
									<div class="hr hr-dotted"></div>
									<div class="space-2"></div>
									
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="itemname"><font color="red">*</font>商品名称：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="itemname" id="itemname" value=""
													 class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="itemsize">规格:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="itemsize" id="itemsize"
												  	class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="unitno">单位:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="unitno" id="unitno"
												 class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

								<div class="space-2"></div>
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="productarea">产地：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="productarea" id="productarea" 
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="hr hr-dotted"></div>
										<div class="row-fluid center">
											<input type="submit" class="btn btn-sm btn-primary" value="保存"  id="submitBtn" /> &nbsp;
												<!--<button id="submitBtn" onclick="submitGo()"
					class="btn btn-success btn-primary">保存</button>&nbsp;-->
										</div>
														
								</form>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- page specific plugin scripts -->

	<script src="${ctx }/js/jquery.form.js"></script>
	<script src="${ctx }/js/jquery.validate.min.js"></script>
	<script src="${ctx }/js/select2.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		var api = frameElement.api, W = api.opener;
		var tolisturl = "${ctx}/foodmanage/foodno_list.do";
		jQuery(function($) {
			$("#submitBtn").on('click', function() {
				if (!$('#foodnoForm').valid()) {
					return false;
				}

			});

			$("#foodnoForm")
					.validate(
							{
								errorElement : 'div',
								errorClass : 'help-block',
								focusInvalid : false,
								rules : {
									itemname : {
										required : true,
										maxlength : 255
									},
									
								},

								messages : {
									itemname : {
										required : "请填写商品名！",
										maxlength : "您设置的商品名过长！"
									},
									
								},

								highlight : function(e) {
									$(e).closest('.form-group').removeClass(
											'has-info').addClass('has-error');
								},

								success : function(e) {
									$(e).closest('.form-group').removeClass(
											'has-error').addClass('has-info');
									$(e).remove();
								},

								errorPlacement : function(error, element) {
									if (element.is(':checkbox')
											|| element.is(':radio')) {
										var controls = element
												.closest('div[class*="col-"]');
										if (controls.find(':checkbox,:radio').length > 1)
											controls.append(error);
										else
											error.insertAfter(element.nextAll(
													'.lbl:eq(0)').eq(0));
									} else
										error.insertAfter(element.parent());
								},

								submitHandler : function(form) {

									$(form)
											.ajaxSubmit(
													{
														type : 'POST',
														url : "${ctx}/foodmanage/foodno_save.do",
														dataType : 'json',
														error : function() {
															$.gritter
																	.add({
																		title : "",
																		text : "系统异常，请联系管理员!",
																		before_open : function() {
																			if ($('.gritter-item-wrapper').length >= 1) {
																				return false;
																			}
																		},
																		class_name : 'gritter-error'
																	});
															return;
														},

														success : function(data) {
															$.gritter
																	.add({
																		title : "",
																		text : data.msg,
																		before_open : function() {
																			if ($('.gritter-item-wrapper').length >= 1) {
																				return false;
																			}
																		},
																		class_name : 'gritter-success'
																	});
															setTimeout(
																	"api.reload(W,tolisturl)",
																	1000);
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