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
	<div class="page-content">
		<form action="${ctx }/xapp/html5_list.do" method="post" id="userForm">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="table-header">
					<a id="addnewButton" class="white " style="cursor:pointer;" href="javascript:addUser();">
					<i class="icon-plus"></i>&nbsp;添加
					</a> &nbsp;|&nbsp;
					<a id="ajaxDelete" class="white" style="cursor:pointer;" href="javascript:ajaxDelete();">
					<i class="icon-trash "></i>&nbsp;删除
					</a> 
					</div>
				<%-- 	<div class="table-responsive">
						<table id="user-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox" class="ace" /> <span class="lbl"></span> </label></th>
									<th>登录名<my:order orderattr="user.chUserLogname" /></th>
									<th class="hidden-320">姓名</th>
									<th >角色</th>

									<th class="hidden-480"><i class="icon-time bigger-110 hidden-480"></i>创建时间<my:order orderattr="user.chUserMaketime"/></th>
									<th class="hidden-480">状态</th>

									<th>操作</th>
								</tr>
							</thead> --%>

						<%-- 	<tbody>
								<c:forEach items="${page.list}" var="user" varStatus="status">
									<tr id="${user.id }" param="ids=${user.id}">
										<td class="center"><label> <input type="checkbox" 
												class="ace" id="ids" name="ids" value="${user.id}" /> <span
												class="lbl"></span> <input type="hidden" id="roleID"
												name="roleID" value="${user.chRole.id}" /> </label></td>

										<td>${user.chUserLogname}</td>
										<td class="hidden-320">${user.chUsername}</td>
										<td>
											<c:if test="${user.chUserRoleids!=null}">
												<c:forEach items="${user.chRoles}" var="chRole"
													varStatus="status">${chRole.chRoleName}
												</c:forEach>
											</c:if>
										</td>
										<td class="hidden-480">${user.chUserMaketime}</td>

										<td class="hidden-480">
											<c:choose>
												<c:when test="${user.chUserState == '正常'}">
													<span class="label label-sm label-warning">${user.chUserState}
													</span>
												</c:when>
												<c:otherwise>
													<span class="label label-sm label-danger">${user.chUserState}
													</span>
												</c:otherwise>
											</c:choose></td>

										<td>
											<div
												class="action-buttons">
												<a class="green"
													href="javascript:editUser('${user.id}');" title="编辑"> <i class="icon-pencil bigger-130"></i></a>
											</div>
											</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="pull-right">
									<my:page page="${page}" />
						</div> 
						
					</div>
						--%>
						 <div class="row-fluid">
				<div class="span5">
					<div class="btn-group">
						<input type="button" class="btn btn-sm btn-primary"  value="<my:i18n zhText="编辑" enText="Edit"/>" id="editButton"
						<my:noAuth funcId="${currentFuncId}" buttonCode="edit">disabled="disabled"</my:noAuth> />
			 	      	<input type="button" class="btn btn-sm btn-primary"  value="<my:i18n zhText="删除" enText="Delete"/>" id="deleteButtn"
			 	      	<my:noAuth funcId="${currentFuncId}" buttonCode="delete">disabled="disabled"</my:noAuth> />
						<input type="button" class="btn btn-sm btn-primary"  value="<my:i18n zhText="添加" enText="Add"/>" id="addButton"
						<my:noAuth funcId="${currentFuncId}" buttonCode="add">disabled="disabled"</my:noAuth> />
					</div>
				</div>
				<div class="span7">
					<div class="lpage"><%-- <my:page page="${page}"/> --%></div>  
				</div>
			</div>
					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</form>
	</div>
	<!-- /.page-content -->
	<!-- page specific plugin scripts -->
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
	$.dialog.setting.extendDrag = true;
	var deleteUrl = "${ctx}/xapp/user_delete.do";
	function add(){
			$.dialog({
				lock:true,
				drag: true,
			 	resize: true,
			 	min:false,
			 	max:false,
			 	width:'800px',
			 	height: '503px',
			    id:'addUser',
			    title:'添加用户',
			    content: 'url:${ctx }/system/user_add.do'
			});
		}
	
	function editUser(id){
			$.dialog({
				lock:true,
				drag: true,
				resize: true,
				min:false,
			 	max:false,
				width: '800px',
				height: '503px',
				id:'editUser',
				title:'用户编辑',
				content: 'url:${ctx }/system/user_update.do?id='+id
			});
		}
	function ajaxDelete(){
				var ids="";
				$('input[type="checkbox"][name="ids"]:checked').each(
					function() {
						ids +=","+$(this).val();
					}
				);
				
		if (ids == "") {
				$.gritter.add({
					title : "",
					text : "请选择需要删除的数据!",
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
			if (ids.indexOf(",") == 0) {
				ids = ids.substring(1);
			}
			$.dialog.confirm("确定要删除选中数据吗？", function() {
				$.ajax({
					url : deleteUrl + "?ids=" + ids,
					type : 'GET',
					error : function() {
						$.gritter.add({
						  title : "",
						  text : "系统异常，请联系管理员！",
						  sticky : false,
						  before_open : function() {
							  if ($('.gritter-item-wrapper').length >= 1) {
								  return false;
							  }
						  },
						  class_name : 'gritter-success '
						});
							return;
					},
					success : function(data) {
						//data = $.parseJSON(data);

						//不存在msg时，当作异常处理
						if (data.msg == undefined) {
						$.gritter.add({
						  title : "",
						  text : "系统异常，请联系管理员！",
						  sticky : false,
						  before_open : function() {
							  if ($('.gritter-item-wrapper').length >= 1) {
								  return false;
							  }
						  },
						  class_name : 'gritter-success '
						});
						return;
						}
						//删除成功提示信息
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
						setTimeout("window.location.reload()", 700);
					}
				});
			});
		}
		jQuery(function($) {
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});
		});
	</script>
</body>
</html>