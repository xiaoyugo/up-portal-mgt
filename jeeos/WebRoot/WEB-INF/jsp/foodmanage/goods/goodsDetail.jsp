<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />
</head>
<body>
	<div class="row">
		<div class="span12">
			<div class="widget-box" style="margin: 0;border-bottom: 0;">
				<div class="widget-body"
					style="background-color: transparent;border: 0;">
					<div class="widget-header widget-header-blue widget-header-flat">
						<h3 class="lighter block green">请编辑商品信息</h3>
					</div>
					<div class="widget-main">
						<div class="row-fluid position-relative">
							<form class="form-horizontal" id="goodsform" method="post">
								<!--  		  <input type="hidden" name="id" id="goodsId" value="${id }">
									 <input type="hidden" value="${modelId}" id="hiddenModelId"/>-->

								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="itemno"><font color="red">*</font>商品条形码：</label>

									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input type="text" id="itemno" name="itemno"
												value="${itemno}" class="col-xs-12 col-sm-6" />
										</div>
									</div>
								</div>

								<div class="space-2"></div>

								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="itemname"><font color="red">*</font>商品名称：</label>

									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input class="col-xs-12 col-sm-6" type="text" id="itemname"
												name="itemname" value="${itemname}" />
										</div>
									</div>
								</div>

								<div class="space-2"></div>

								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="itemsize">规格：</label>

									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input class="col-xs-12 col-sm-6" type="text" id="itemsize"
												name="itemsize" value="${itemsize }" />
										</div>
									</div>
								</div>
								<div class="space-2"></div>

								<div class="form-group">
									<label
										class="control-label col-xs-12 col-sm-5 no-padding-right"
										for="unitno">单位：</label>

									<div class="col-xs-12 col-sm-6">
										<div class="clearfix">
											<input class="col-xs-12 col-sm-6" type="text" id="unitno"
												name="unitno" value="${unitno }" />
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
											<input class="col-xs-12 col-sm-6" type="text"
												id="productarea" name="productarea" value="${productarea }" />
										</div>
									</div>
								</div>

								<div class="hr hr-dotted"></div>
								<div class="row-fluid center">
									<input type="submit" class="btn btn-sm btn-primary" value="更新"
										id="submitBtn" /> &nbsp;
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
		var tolisturl = "${ctx}/foodmanage/goodsmanage_list.do";
		jQuery(function($) {
			$("#submitBtn").on('click', function() {
				if (!$('#goodsform').valid()) {
					return false;
				}

			});

			$("#goodsform")
					.validate(
							{
								errorElement : 'div',
								errorClass : 'help-block',
								focusInvalid : false,
								rules : {
									itemno : {
										required : true,
										maxlength : 30
									},
									itemname : {
										required : true,
										maxlength : 255
									},
									
								},

								messages : {
									itemno : {
										required : "请填写商品编号！",
										maxlength : "商品编号过长！"
									},
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
														url : "${ctx}/foodmanage/goodsmanage_save.do",
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