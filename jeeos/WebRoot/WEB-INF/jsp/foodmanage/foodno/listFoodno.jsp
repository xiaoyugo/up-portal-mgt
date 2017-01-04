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
		<form action="${ctx }/foodmanage/foodno_list.do" method="get" id="foodnoForm">
			<div class="row">
				<div class="col-xs-12">
				<table class="table table-bordered" style="width: 100%;margin-bottom: 0;">
			<tr style="text-align: center;vertical-align: middle; ">
				<td style="text-align: left;vertical-align: top; ">未识别条形码：<input type="text" name="queryitem" id="queryitem" value="${queryitem}" style="width:200px"/>
					&nbsp;&nbsp;&nbsp;
					<button id="submitBtn" type="submit" class="btn-primary btn-mini"
						title="Search">
						<i class="icon-search icon-white">&nbsp;&nbsp;查询</i>
					</button>
					 </td>

			</tr>
		</table>
					<div class="table-responsive">
						<table id="device-list-table"
							class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
					               
					                <th><my:i18n zhText="未识别条形码" enText="Foodno Name" /> <my:order
											orderattr="itemno" />
									</th>
									<th>时间</th>
									<th><my:i18n zhText="操作" enText="Foodno Opertion" /></th>
								</tr>
							</thead>
							<tbody >
								<c:forEach items="${page.list}" var="itemno" varStatus="status">
									<tr id="${itemno}" param="ids=${user.id}">
										<td>${itemno.barcode}</td>
										<td>${itemno.ctime}</td>
										<td><div class="action-buttons">	
													<a class="green" href="javascript:editFood('${itemno.barcode}');" title="编辑">
													<i class="icon-pencil bigger-130"></i> </a>
													<a class="red" href="javascript:deleteFood('${itemno.barcode}');" title="删除">
													<i class="icon-trash bigger-130"></i> </a>
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
		var deleteUrl = "${ctx }/foodmanage/foodno_delete.do";

		function editFood(id) {
			$.dialog({
				lock : true,
				drag : true,
				resize : true,
				min : false,
				max : false,
				id : 'editFunction',
				title : '编辑条形码',
				content : 'url:${ctx }/foodmanage/foodno_update.do?id=' + id,
				width : '800px',
				height : 400,
				
			});

		};

function deleteFood(id) {
			$.dialog.confirm("确定要删除选中数据吗？", function() {
				$.ajax({
					url : deleteUrl + "?id=" + id,
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