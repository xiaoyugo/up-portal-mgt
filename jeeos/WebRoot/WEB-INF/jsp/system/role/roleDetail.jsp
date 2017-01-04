<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<%@ taglib prefix="my" uri="/mytags"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />
<!-- page specific plugin CSS -->
<link rel="stylesheet" href="${ctx}/js/zTree/2.0/zTreeStyle.css"
	type="text/css"></link>
<script type="text/javascript"
	src="${ctx}/js/zTree/2.0/jquery.ztree-2.6.min.js"></script>

<!-- inline scripts related to this page -->
<script type="text/javascript">
	 var treeurl="${ctx}/system/role_getMenu.do";
     var zTree;

	$(function(){
		var api = frameElement.api, W = api.opener,cDG;
		addFuncContent('1');
		if($("#hiddenModelId").val()){
			addFuncContent($("#hiddenModelId").val());			
		}
	});
	
	function addFuncContent(modelId) {
		var roleId = $("#roleId").val();
        $.ajax({
			url: "${ctx}/system/model_funcList.do?modelId="+modelId+"&roleId="+roleId,
			type: 'GET',
			dataType:"json",		
	        error : function() {
						$.gritter.add({
							title : "",
							text : "系统异常，查询权限列表失败!",
							before_open : function() {
								if ($('.gritter-item-wrapper').length >= 1) {
									return false;
								}
							},
							class_name : 'gritter-error'
						});
					},
					success : function(result) {
						$("#tree").empty();
						var zNodes;
						var treeNodeArray = new Array();
						var setting = {
							isSimpleData : true,
							checkable : true,
							treeNodeKey : "id",
							treeNodeParentKey : "pId",
						};
						var checkIds = result.checkIds;
						var checkedIdArray = new Array();
						if (checkIds) {
							checkedIdArray = checkIds.split(",");
						}
						var data = result.funcList;
						if (data != null && data.length > 0) {
							for ( var i = 0; i < data.length; i++) {
								var func = data[i];
								var pId = func.parentFuncId;
								var id = func.id;
								var name = "<my:i18n zhText='"+func.chFuncName+"' enText='"+func.chFuncName+"'/>";
								var _url = func.chFuncPath;
								var checked = false;
								if ($.inArray(id, checkedIdArray) != -1) {
									checked = true;
								}
								var oneTreeNode = '{id:"' + id + '",pId:"'
										+ pId + '",name:"' + name
										+ '",checked:' + checked + ',_url:"'
										+ _url + '"}';
								treeNodeArray.push(oneTreeNode);
							}
							var zNodeStr = '[' + treeNodeArray.join(",") + ']';
							var zNodes = (new Function("return " + zNodeStr))();
							setting.expandSpeed = ('undefined' == typeof (document.body.style.maxHeight)) ? ""
									: "fast";
							zTree = $("#tree").zTree(setting, zNodes);
						}
					}
				});
	}
</script>
</head>
<body>
	<div class="row">
		<div class="span12">
			<div class="widget-box" style="margin: 0;border-bottom: 0;">
				<div class="widget-body" style="background-color: transparent;border: 0;">
				<div class="widget-header widget-header-blue widget-header-flat">
						<h3 class="lighter block green">请编辑角色信息</h3>
					</div>
					<div class="widget-main">
						<div class="row-fluid position-relative">
								<form  class="form-horizontal" id="roleForm" action="system/role_save.do" method="post">
									  <input type="hidden" name="id" id="roleId" value="${id }">
									 <input type="hidden" value="${modelId}" id="hiddenModelId"/>
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
											<c:if test="${chRoleFlag == 'Y'}">
												<input type="radio" id="chRoleFlag" name="chRoleFlag"
													checked="checked" value="Y" />是&nbsp;&nbsp;
                <input type="radio" id="chRoleFlag" name="chRoleFlag"
													value="N" />否
             </c:if>
											<c:if test="${chRoleFlag == 'N'}">
												<input style="margin-top: 0;" type="radio" id="chRoleFlag"
													name="chRoleFlag" value="Y" />是&nbsp;&nbsp;
                <input style="margin-top: 0;" type="radio"
													id="chRoleFlag" name="chRoleFlag" checked="checked"
													value="N" />否
             </c:if>
										</div>
									</div>
								</div>
								<div class="hr hr-dotted"></div>

									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right">角色描述：</label>

										<div class="col-xs-12 col-sm-6">
											<div class="clearfix">
											<textarea class="col-xs-12 col-sm-6"  name="chRoleDesc" id="chRoleDesc">${chRoleDesc }</textarea>
										</div>
										</div>
									</div>
									<div class="form-group">
										<label
											class="control-label col-xs-12 col-sm-5 no-padding-right"><font color="red">*</font>可访问权限：</label>

										<div class="col-xs-11 col-sm-6">
											<div >
											 <ul id="tree" class="tree" style="margin-left: 0px;"></ul>
		              						<input type="hidden" name="checkIds" id="checkIds" value="" />
										</div>
										</div>
									</div> 
									<div class="hr hr-dotted"></div>
										<div class="row-fluid center">
											<input type="submit" class="btn btn-sm btn-primary" value="保存"  id="submitBtn" /> &nbsp; 
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
	<script type="text/javascript"
		src="${ctx}/js/zTree/2.0/jquery.ztree-2.6.min.js"></script>

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
			                               title : "系统提示",
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
			                               title : "系统提示",
			                               text : "操作失败，请联系管理员!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
											return;
										},

										beforeSubmit : function() {

										},
										success : function(data) {
										    $.gritter.add({
			                               title : "",
			                               text : data.msg,
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-success'
			                                });
			                                setTimeout("api.reload(W, tolistUrl)", 1000);
											
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