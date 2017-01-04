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
								<form  class="form-horizontal" id="validation-form" method="post">
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="faultcode"><font color="red">*</font>故障码：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="faultcode" id="faultcode"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>
									
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="faultvalue"><font color="red">*</font>故障值：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="faultvalue" id="faultvalue"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="faultdescribe"><font color="red">*</font>故障描述:</label>

										<div class="col-xs-12 col-sm-6" >
											<div class="clearfix">
												<textarea  name="faultdescribe" id="faultdescribe"
													class="col-xs-12 col-sm-6" style="height:100px">
												</textarea>
											</div>
										</div>
									</div>									
									
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
	var tolisturl = "${ctx}/waterclean/fault_list.do";

			 jQuery(function($) {
		 		$(".select2").css('width','300px').select2({allowClear:true}).on('change', function(){
					$(this).closest('form').validate().element($(this));
				});  
		 	 jQuery.validator.addMethod("hexnum", function(value, element) { 
		 		var hexn = /^([a-fA-Z0-9]+)$/;     
                return this.optional(element) || (hexn.test(value));    
		 	    }, "Please enter a valid hex num");    
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
						faultcode: {
							required: true,
							maxlength:4,
							hexnum:true 
						},
						faultvalue: {
							required: true,
							maxlength:2,
							hexnum:true 
						},
						faultdescribe: {
							required: true,
						},
						/* roleIds: {
							required: true
						}, */
					},
			
					messages: {
					faultcode: {
							required: "请填故障码！",
							maxlength:"故障码范围:0000-FFFF",
							hexnum:"请输入合法的16进制数！"
						},
						faultvalue: {
							required:"请填故障值！",
							maxlength:"故障值范围:00-FF",
							hexnum:"请输入合法的16进制数！"
						},
						faultdescribe: {
							required: "请填写故障描述！"
						},						
						/* roleIds: "请至少选择一项！", */
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
			
					submitHandler: function (form) {
							    var faultcode=document.getElementById("faultcode").value;
							    var faultvalue=document.getElementById("faultvalue").value;
						    	$.ajax({
									url: "${ctx}/waterclean/fault_isFaultExist.do?faultcode="+faultcode+"&faultvalue="+faultvalue,
									type: 'GET',
									dataType:"json",
									error: function(){
										    $.gritter.add({
			                               title : "",
			                               text : "系统异常，请联系管理员!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
										return;
									},
									success: function(data){
										if(data!=null&&data.msg>0){
										$.gritter.add({
			                               title : "",
			                               text : "该故障存在，系统已放弃本次操作!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
											return;
										}else{  
											$(form).ajaxSubmit({
												type : 'POST',
												url : "${ctx}/waterclean/fault_save.do",
												dataType:  'json', 
												error: function(){
													
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
		                                                                    api.close();
																				return;
																			},
																			success : function() {
																				$.gritter
																						.add({
																							title : "",
																							text : "添加成功!",
																							before_open : function() {
																								if ($('.gritter-item-wrapper').length >= 1) {
																									return false;
																								}
																							},
																							class_name : 'gritter-success'
																						});
																						setTimeout("api.reload(W,tolisturl)", 1000);
																				
																				 // api.close(); 
																			}
																		});

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