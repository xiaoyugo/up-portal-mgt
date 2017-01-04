<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<%@ taglib prefix="my" uri="/mytags"  %>
<!DOCTYPE html>
<head >
<meta name="decorator" content="default" />
<!-- page specific plugin CSS -->
<link rel="stylesheet" href="${ctx}/js/zTree/2.0/zTreeStyle.css" type="text/css"></link>
	
	<!-- inline scripts related to this page -->
	
	<script type="text/javascript">
	$(function(){
		$("#chRoleModel").bind('change',function(){
  				addFuncContent($(this).val());
  			});
		
	});
	
	function addFuncContent(modelId) {
        $.ajax({
			url: "${ctx}/system/model_funcList.do?modelId="+modelId,
			type: 'GET',
			dataType:"json",
			error: function(){
				alert("获取权限范围失败");
			},
			success: function(result){
				$("#tree").empty();
				var zNodes;
				var treeNodeArray = new Array();
				var setting = {
					isSimpleData: true,
					checkable: true,
					treeNodeKey: "id",
					treeNodeParentKey: "pId",
				};
				var data = result.funcList;
				if(data!=null && data.length>0) {
	                for(var i=0; i<data.length; i++) {
	                   	var func = data[i];
	                   	var pId = "";
	                   	if (func.chFunc != null)
	                   	    pId = func.chFunc.id;
					    var id = func.id;
					    var name = "<my:i18n zhText='"+func.chFuncName+"' enText='"+func.chFuncName+"'/>";
					    var _url = func.chFuncPath;
					    var checked =false;
					    var oneTreeNode='{id:"'+id+'",pId:"'+pId+'",name:"'+name+'",checked:'+checked+',_url:"'+_url+'"}';
					    treeNodeArray.push(oneTreeNode);
	                }
	                var zNodeStr = '['+treeNodeArray.join(",")+']';
	   			 		var zNodes = (new Function("return " + zNodeStr))();
	                setting.expandSpeed = ('undefined' == typeof(document.body.style.maxHeight))?"":"fast";
	                zTree = $("#tree").zTree(setting, zNodes);
            	}
			}
		});
  		}
  		
  		window.onload = function(){
  			addFuncContent('1');
  		};
	</script>

</head>
<body>
	<div class="row">
		<div class="span12">
			<div class="widget-box" style="margin: 0;border-bottom: 0;">
				<div class="widget-body" style="background-color: transparent;border: 0;">
				<div class="widget-header widget-header-blue widget-header-flat">
						<h3 class="lighter block green">请填写角色信息</h3>
					</div>
					<div class="widget-main">
						<div class="row-fluid position-relative">
								<form  class="form-horizontal" id="roleForm"  method="post">
									 <input type="hidden" name="checkIds" id="checkIds" value="" />
									 <input type="hidden" name="chRole.id" id="parentRoleId" value="${chRole.id }">
								<c:if test="${!empty chRole.chRoleName}">
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chUserLogname">上级角色：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="text" value="${chRole.chRoleName}" readonly="readonly"
													class="col-xs-12 col-sm-6" />
											</div>
										</div>
									</div>
								</c:if>
									<div class="space-2"></div>
									
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chRoleName"><font color="red">*</font>角色名称：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
											<input class="col-xs-12 col-sm-6"  type="text" id="chRoleName" name="chRoleName" value="${chRoleName }" />
									        </div>
										 </div>
									</div>
									
								<div class="space-2"></div>
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"
											for="chRoleFlag">是否激活：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
												<input type="radio" id="chRoleFlag" name="chRoleFlag" value="Y" checked="checked"/>是&nbsp;&nbsp;<input  type="radio" id="chRoleFlag" name="chRoleFlag" value="N" />否
											</div>
										</div>
									</div>
									<div class="hr hr-dotted"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right">角色描述：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
											<textarea class="col-xs-12 col-sm-6"  name="chRoleDesc" id="chRoleDesc"></textarea>
										</div>
										</div>
									</div>
									<div class="col-xs-12">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"><font color="red">*</font>可访问权限：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
											 <ul id="tree" class="tree" style="margin-left: 0px;"></ul>
		              						
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
	<script type="text/javascript" src="${ctx}/js/zTree/2.0/jquery.ztree-2.6.min.js"></script>
	
	<!-- inline scripts related to this page -->
	
	<script type="text/javascript">
	var api = frameElement.api, W = api.opener;
	var tolistUrl = "${ctx}/system/role_list.do";
	
	 jQuery(function($) {
				$("#submitBtn").on('click' , function(){
						if(!$('#roleForm').valid()) {
							return false;
						}
						
				});
				
				$("#roleForm").validate({
					errorElement: 'div',
					errorClass: 'help-block',
					focusInvalid: false,
					rules: {
						chRoleName: {
							required: true,
						},
					},
					messages: {
					    chRoleName: {
							required: "请填写角色名称！",
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
			
					submitHandler: function (form) {
									var checkedNodes = zTree.getCheckedNodes();
									if (checkedNodes.length == 0) {
									 $.gritter.add({
			                               title : "",
			                               text : "请至少选择一个权限!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
										return false;
									}
									var checkedNodeArray = [];
									for ( var i = 0; i < checkedNodes.length; i++) {
										checkedNodeArray
												.push(checkedNodes[i].id);
									}
									var checkIds = checkedNodeArray.join(",");
									$("#checkIds").val(checkIds);
									
									$(form).ajaxSubmit({
										url : "${ctx}/system/role_save.do",
										type : 'POST',
										dataType : 'json',
										error : function() {
											 $.gritter.add({
			                               title : "",
			                               text : "系统异常，系统已放弃本次操作!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
										return false;										},
										beforeSubmit : function() {
										},
		                                success : function(data) {
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
														   setTimeout("api.reload(W,tolistUrl)", 1000);
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