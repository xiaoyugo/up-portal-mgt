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
											for="chUserLogname"><font color="red">*</font>登录名：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="chUserLogname" id="chUserLogname"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>
									
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chUsername"><font color="red">*</font>姓名：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="chUsername" id="chUsername"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chUserPassword"><font color="red">*</font>密码:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="password" name="chUserPassword" id="chUserPassword"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

									<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chUserPassword2"><font color="red">*</font>密码确认:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="password" name="chUserPassword2" id="chUserPassword2"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>

								<div class="space-2"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="platform">用户状态:</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<select class="input-medium " id="chUserState" name="chUserState">
													<option value="正常" selected="selected">正常</option>
													<option value="锁定">锁定</option>
												</select>
											</div>
										</div>
									</div>
								<div class="space-2"></div>
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chUserSortno">排序编号：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" name="chUserSortno" id="chUserSortno" 
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>
									<div class="hr hr-dotted"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right">角色列表</label>

										<div class="col-xs-12 col-sm-6">
											<c:forEach items="${roleList}" var="role">
												<div id="${role.id }">
													<label>
														<input name="roleIds" type="checkbox" value="${role.id}" class="ace">
														<span class="lbl"> ${role.chRoleName }</span>
													</label>
												</div>
										</c:forEach>
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
	var tolisturl = "${ctx}/system/user_list.do";

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
						chUserLogname: {
							required: true,
							maxlength:50
						},
						chUsername: {
							required: true,
							maxlength:50
						},
						chUserPassword: {
							required: true,
							minlength: 3
						},
						chUserPassword2: {
							required: true,
							equalTo: "#chUserPassword"
						},
						roleIds: {
							required: true
						},
					},
			
					messages: {
					chUserLogname: {
							required: "请填写登陆名！",
							maxlength:"您设置的登录名过长！"
						},
						chUsername: {
							required:"请填写用户名！",
							maxlength:"您设置的用户名过长！"
						},
						chUserPassword: {
							required: "请指定一个密码！",
							minlength: "您设置的密码过短！"
						},
						chUserPassword2: {
							required: "请您确认密码！",
							equalTo: "前后密码不一致！"
						},
						roleIds: "请至少选择一项！",
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
								var userName=document.getElementById("chUserLogname").value;
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
										}else{
											$(form).ajaxSubmit({
												type : 'POST',
												url : "${ctx}/system/user_save.do",
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
																				
																				/*  api.close();  */
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