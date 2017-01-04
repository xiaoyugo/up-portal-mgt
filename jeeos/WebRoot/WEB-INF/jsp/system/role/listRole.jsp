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
		<form action="${ctx }/system/role_list.do" method="post" id="roleForm">
			<div class="row">
				<div class="col-xs-12">
					<div class="table-header">
						<a id="addnewButton" class="white "
							style="cursor:pointer;"
							href="javascript:addRole();"> <i class="icon-plus"></i>&nbsp;添加
						</a> &nbsp;|&nbsp; <a id="ajaxDelete" class="white"
							style="cursor:pointer;"
							href="javascript:ajaxDelete();"> <i class="icon-trash "></i>&nbsp;删除
						</a>
					</div>
					<div class="table-responsive">
						<input type="hidden" name="id" value="${id }" />
						<table id="roleListTable"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox"
											class="ace" /> <span class="lbl"></span> </label>
									</th>
									<th><my:i18n zhText="角色名称" enText="Role Name" /> <my:order
											orderattr="role.chRoleName" />
									</th>
									<th class="hidden-320"><my:i18n zhText="是否激活" enText="Role Flag" /> <my:order
											orderattr="role.chRoleFlag" />
									</th>
									<th class="hidden-480"><my:i18n zhText="描述" enText="Role Description" /> <my:order
											orderattr="role.chRoleDesc" />
									</th>
									<th><my:i18n zhText="操作" enText="Role Opertion" />
									</th>
								</tr>
							</thead>
							<tbody >
								<c:if test="${empty page.list}">
									<tr>
										<td align="center" colspan="7"><font color="red"><my:i18n
													zhText="当前角色没有下级角色" enText="There are no Sub Roles" /> </font></td>
									</tr>
								</c:if>
								<c:forEach items="${page.list}" var="role" varStatus="status">
									<tr id="${role.id }" param="ids=${user.id}">
										<td align="center"><label><input type="checkbox" class="ace"
											id="ids" name="ids" value="${role.id}" /> <span class="lbl"></span></label>
										</td>
										<td>${role.chRoleName}</td>
										<td class="hidden-320">${role.chRoleFlag}</td>
										<td class="hidden-480">${role.chRoleDesc}</td>
										<td><div class="action-buttons">
												<a class="green" href="javascript:editRole('${role.id}');" title="编辑">
													<i class="icon-pencil bigger-130"></i> </a>
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
				</div>
			</div>
		</form>
	</div>

	<!-- page specific plugin scripts -->
	<script type="text/javascript" src="${ctx}/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/jquery.dataTables.bootstrap.js"></script>
	<script type="text/javascript"
		src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	<script type="text/javascript"
		src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>

	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		$.dialog.setting.extendDrag = true;
		var deleteUrl = "${ctx }/system/role_delete.do";
		function addRole() {
			var id = $("input[name='id']").val();
			$.dialog({
				lock : true,
				drag : true,
				resize : true,
				min : false,
				max : false,
				id : 'addRole',
				title : '新增角色',
				content : 'url:${ctx }/system/role_add.do?id=' + id,
				width : '900px',
				height : 400
			});
		};

		function editRole(id) {
			$.dialog({
				lock : true,
				drag : true,
				resize : true,
				min : false,
				max : false,
				id : 'editFunction',
				title : '编辑角色',
				content : 'url:${ctx }/system/role_update.do?id=' + id,
				width : '900px',
				height : 400,
				
			});

		};

		function ajaxDelete() {
			var ids = "";
			$('input[type="checkbox"][name="ids"]:checked').each(function() {
				ids += "," + $(this).val();
			});
			if (ids == "") {
				$.dialog.alert("请选择需要删除的数据!");
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
			                               text : "系统异常，系统已放弃本次操作!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
					                       },
					success : function(data) {
						//data = $.parseJSON(data);

						//不存在msg时，当作异常处理
						if (data.msg == undefined) {
						     $.gritter.add({
			                               title : "",
			                               text : "系统异常，请联系管理员！",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
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
						setTimeout("window.location.reload()", 1000);
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