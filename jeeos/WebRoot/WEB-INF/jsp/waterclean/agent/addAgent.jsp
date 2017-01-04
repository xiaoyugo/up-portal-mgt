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
											for="agentname"><font color="red">*</font>代理商名字：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="agentname" id="agentname"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>
									
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="agentphone"><font color="red">*</font>电话：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="agentphone" id="agentphone"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="agentadress"><font color="red">*</font>所在地:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="agentadress" id="agentadress"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>
									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="platform">关联设备ID:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<select class="input-large" id="sn" name="sn">
												<c:forEach items="${snlist}" var="devicesn">
													<option value="${devicesn}">${devicesn}</option>
												</c:forEach>
												</select>
											</div>
										</div>
									</div>
									<div class="hr hr-dotted"></div>

									<%--<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right">关联设备id</label>

										 <div class="col-xs-12 col-sm-6">
											<c:forEach items="${deviceList}" var="device">
												<div id="${device.sn }">
													<label>
														<input name="device" type="checkbox" value="${device.sn}" class="ace">
														<span class="lbl"> ${device.sn }</span>
													</label>
												</div>
										</c:forEach>
										</div> 
									</div>--%>
									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="platform">代理商级别:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<select class="input-medium " id="agentclass" name="agentclass">
													<option value="1级" selected="selected">1级</option>
													<option value="2级">2级</option>
												</select>
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
	var tolisturl = "${ctx}/waterclean/agent_list.do";

			 jQuery(function($) {
		 		$(".select2").css('width','300px').select2({allowClear:true}).on('change', function(){
					$(this).closest('form').validate().element($(this));
				});  
				
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
						agentname: {
							required: true,
							maxlength:50
						},
						agentphone: {
							required: true,
							maxlength:11
						},
						agentadress: {
							required: true,
							minlength: 3
						},
						/* roleIds: {
							required: true
						}, */
					},
			
					messages: {
					agentname: {
							required: "请填写代理商名字！",
							maxlength:"您设置的登录名过长！"
						},
						agentphone: {
							required:"请填写代理商电话！",
							maxlength:"您填写的电话过长！"
						},
						agentadress: {
							required: "请填写地址！",
							minlength: "您的地址过短！"
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
								 /* var userName=document.getElementById("agentname").value;
						    	$.ajax({
									url: "${ctx}/system/user_findUserByLogName.do?userName="+userName,
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
										if(data!=null&&data.length>0){
										$.gritter.add({
			                               title : "",
			                               text : "该用户已经存在，系统已放弃本次操作!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
											return;
										}else{  */
											$(form).ajaxSubmit({
												type : 'POST',
												url : "${ctx}/waterclean/agent_save.do",
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

													/* }
												}
											}); */

								},
								invalidHandler : function(form) {
								}
							});
		});
	</script>
	
</body>
</html>