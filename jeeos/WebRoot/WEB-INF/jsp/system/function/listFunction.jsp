<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<%@ taglib prefix="my" uri="/mytags"%>
<!DOCTYPE html>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
<script type="text/javascript"
	src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>

<script type="text/javascript">
		var deleteUrl = "${ctx }/system/func_delete.do";
		var ids = "";
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
		


	$(function() {
		$("#ajaxDelete").bind('click', function(){
			ajaxDelete();
		});
	});

	function ajaxDelete() {
		
		$('input[type="checkbox"][name="ids"]:checked').each(function() {
			ids += "," + $(this).val();
		});
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
					$.dialog.alert("系统异常，请联系管理员！");
				},
				success : function(data) {
					//data = $.parseJSON(data);

					//不存在msg时，当作异常处理
					if (data.msg == undefined) {
						$.dialog.alert("系统异常，请联系管理员！");
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
					parent.removeNodes(ids);
					//设置部分时间暂停方便用户看清提示消息
				  setTimeout("window.location.reload()",1000); 
				}
			});
		});
		
	}
</script>
</head>
<body id="frameBody">
	<div class="page-content" style="padding-top: 0;">
		<div class="row">
			<div class="col-sm-12">
				<div class="tabbable" id="uldiv">
					<input type="hidden" name="parentId" id="parentId" value="${id }">
					<ul class="nav nav-tabs" id="myTab">
						<li><a href="${ctx }/system/func_update.do?id=${id }">详情
						</a>
						</li>

						<li class="active" id="current"><a
							href="${ctx }/system/func_list.do?id=${id }"> 列表 </a>
						</li>
						<li><a href="${ctx }/system/func_add.do?id=${id }"> 新增 </a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<form action="${ctx }/system/func_list.do" method="post" id="menuForm">
			<input type="hidden" name="id" value="${id }" />
			<table class="table table-striped table-bordered table-hover"
				width="100%">
				<thead class="">
					<tr>
						<th style="text-align: center;"><input type="checkbox"
							class="ace" name="checkId" id="checkId" onclick="selectAll()"
							value=""><span class="lbl"></span></th>
						<th><my:i18n zhText="菜单名称" enText="Menu Name" /></th>
						<th width="30%"><my:i18n zhText="路径" enText="Path" /></th>
						<th class="hidden-480"><my:i18n zhText="备注" enText="Remark" />
						</th>
					</tr>
				</thead>
				<tbody class="">
					<c:if test="${empty page.list}">
						<tr>
							<td align="center" colspan="6"><font color="red"><my:i18n
										zhText="当前菜单没有下级菜单" enText="There are no Sub Menus" /> </font>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${page.list}" var="func" varStatus="status">
						<tr id="${func.id }">
							<td align="center"><input type="checkbox" class="ace"
								name="ids" value="${func.id}" /><span class="lbl"></span></td>
							<td><a href="${ctx }/system/func_update.do?id=${func.id}"
								name="menuDetail"><my:i18n zhText="${func.chFuncName}"
										enText="${func.chFuncName}" /> </a>
							</td>
							<td>${func.chFuncPath}</td>
							<td class="hidden-480">${func.chFuncMemo}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="row_fluid">
				<input type="button" class="btn btn-sm btn-primary"
					id="ajaxDelete" value="批量删除">
				<div class="lpage">
					<my:page page="${page}" />
				</div>
			</div>
		</form>
	</div>
</body>
</html>