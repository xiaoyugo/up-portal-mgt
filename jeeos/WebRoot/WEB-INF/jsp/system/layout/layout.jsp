<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>运营管理平台</title>
<meta name="decorator" content="default" />
<style>
iframe {
	height: 100%;
	width: 100%;
	border: 0 none;
}
</style>
<script type="text/javascript">
	$.dialog.setting.extendDrag = true;
	function modifyPWD() {
		$.dialog({
			lock : true,
			drag : true,
			resize : true,
			min : false,
			max : false,
			width : '800px',
			height : '300px',
			id : 'addUser',
			title : '修改密码',
			content : 'url:${ctx }/system/user_toUpdatePassword.do'
		});
	}
</script>

</head>
<body>
	<div class="navbar " id="navbar">
		<div class="navbar-container " id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small><i
						class="icon-star bigger-120 red"></i>&nbsp;统一运营管理平台 </small> </a>

				<!-- /.brand -->
			</div>
			<!-- /.navbar-header -->

			<div class="navbar-header pull-right" role="navigation">
				<!-- 			<div class="navbar-header pull-right inline"> -->
				<ul class="nav ace-nav">
					<li class="middle-blue"><a data-toggle="dropdown" href="#"
						class="dropdown-toggle"> <img class="nav-user-photo"
							src="${ctx}/avatars/avatar2.png" alt="Jason's Photo" /> <span
							class="user-info"> <small>欢迎光临,</small>${user.chUsername}</span>
							<i class="icon-caret-down"></i> </a>
						<ul
							class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li><a href="javascript:modifyPWD();"> <i
									class="icon-asterisk"></i>&nbsp;修改密码 </a>
							</li>
							<li class="divider"></li>
							<li><a href="${ctx}/login/login_logout.do"> <i
									class="icon-off"></i> 退出 </a>
							</li>
						</ul>
					</li>
				</ul>
				<!-- /.ace-nav -->
			</div>
			<!-- /.navbar-header -->
		</div>
		<!-- /.container -->
	</div>


	<div class="main-container " scrolling="no" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span> </a>
			<div class="sidebar " id="sidebar">
				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'fixed')
					} catch (e) {
					}
				</script>
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<div class="label label-xlg label-success">
							<i class="icon-signal"></i>
						</div>
						<div class="label label-xlg label-info">
							<i class="icon-pencil"></i>
						</div>
						<div class="label label-xlg label-warning">
							<i class="icon-group"></i>
						</div>
						<div class="label label-xlg label-danger">
							<i class="icon-cogs"></i>
						</div>
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span> <span class="btn btn-info"></span>
						<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
					</div>
				</div>
				<!-- #sidebar-shortcuts -->
				<ul class="nav nav-list">
					<c:forEach items="${menuList}" var="menu">
						<li><a href="#" class="dropdown-toggle"> <i
								class="${menu.chFuncImage}"></i> <span class="menu-text">${menu.chFuncName}<span
									class="badge badge-primary"> ${menu.subSize} </span> </span> <b
								class="arrow icon-angle-down"></b> </a> <c:set
								value="${menu.subList}" var="subList"></c:set> <%
 	List list = (List) pageContext.getAttribute("subList");
 		if (list != null && list.size() > 0) {
 %>
							<ul class="submenu">
								<%
									}
								%>
								<c:forEach items="${menu.subList}" var="subMenu">
									<li><c:set value="${subMenu.subList}" var="subSubList"></c:set>
										<%
											List sublist = (List) pageContext
															.getAttribute("subSubList");;
													if (sublist != null && sublist.size() > 0) {
										%> <a href="#" class="dropdown-toggle"> <%
 	} else {
 %> <a href="${subMenu.chFuncPath}" class="menuItem" id="${subMenu.id}">
												<%
													}
												%> <i class="icon-double-angle-right"></i>${subMenu.chFuncName}</a>
											<%
												if (sublist != null && sublist.size() > 0) {
											%>
											<ul class="submenu">
												<%
													}
												%>
												<c:forEach items="${subMenu.subList}" var="subSubMenu">

													<li><a href="${subSubMenu.chFuncPath}"
														class="menuItem" id="${subMenu.id}"> <i
															class="icon-double-angle-right"></i>
															${subSubMenu.chFuncName}</a>
													</li>
												</c:forEach>
												<%
													if (sublist != null && sublist.size() > 0) {
												%>
											</ul> <%
 	}
 %>
									
									</li>

								</c:forEach>
								<%
									if (list != null && list.size() > 0) {
								%>
							</ul> <%
 	}
 %>
						</li>
					</c:forEach>
				</ul>
				<!-- /.nav-list -->

				<div class="sidebar-collapse" id="sidebar-collapse">
					<script type="text/javascript">
						try {
							ace.settings.check('sidebar', 'collapsed')
						} catch (e) {
						}
					</script>
					<i class="icon-double-angle-left"
						data-icon1="icon-double-angle-left"
						data-icon2="icon-double-angle-right"></i>
				</div>


			</div>
			<div class="main-content">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>
					<ul class="breadcrumb" id="breadcrumb">
						<li><i class="icon-home home-icon"></i> <a
							href="${ctx}/system/user_index.do" target="main-content">首页</a>
						</li>
					</ul>
				</div>
				<!-- .breadcrumb -->

				<div class="page-content">
					<div class="row">
						<!-- <div class="col-xs-12"> -->
						<div class="span12" id="body-content" style="height:100%">
							<!-- PAGE CONTENT BEGINS -->


							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- </div> -->
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
			<!-- /.main-content -->
			<div class="ace-settings-container" id="ace-settings-container">
				<div class="btn-xs btn btn-warning ace-settings-btn"
					id="ace-settings-btn">
					<i class="icon-cog bigger-150 "></i>
				</div>

				<div class="ace-settings-box " id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hide">
								<option data-skin="default" value="#2B7DBC">#2B7DBC</option>
								<option data-skin="skin-1" value="#222A2D">#222A2D</option>
								<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>

					<div>
						<input type="checkbox" class="ace ace-checkbox-2"
							id="ace-settings-add-container" /> <label class="lbl"
							for="ace-settings-add-container"> 切换窄屏 </label>
					</div>
				</div>
			</div>
			<!-- /#ace-settings-container -->
			<a href="#" id="btn-scroll-up"
				class="btn-scroll-up btn btn-sm btn-inverse"> <i
				class="icon-double-angle-up icon-only bigger-110"></i> </a>
		</div>
		<%@include file="/common/foot.jsp"%>
		<!-- /.main-container-inner -->
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!-- page specific plugin scripts -->
	<script type="text/javascript"
		src="${ctx}/js/lhgdialog/lhgcore.lhgdialog.min.js?self=false&skin=default"></script>
	<script type="text/javascript"
		src="${ctx}/js/lhgdialog/lhgdialog.alert.js"></script>
	<!-- inline scripts related to this page -->

	<script type="text/javascript">
		var ctx = '${ctx}';

		function iframeOnload(iframe, wrapper) {
			var win = iframe.contentWindow;
			var bh = win.document.body.offsetHeight + 50;
			wrapper.style.height = bh + "px";
		}
		function resizeContentIframe(height) {
			var top_el = document.getElementById("body-content");
			var iframe = document.getElementsByTagName('iframe')[0];
			iframe.style.height = height;
			top_el.style.height = iframe.style.height;
		}
		function createIframe(url, frameName) {
			var top_el = document.getElementById("body-content");
			var iframe = document.createElement("iframe");
			iframe.src = url;
			iframe.name = frameName;
			iframe.id = frameName;
			top_el.innerHTML = '';
			top_el.appendChild(iframe);
			if (!!document.all) {
				iframe.attachEvent("onload", function() {
					iframeOnload(iframe, top_el);
				});
			} else {
				iframe.onload = function() {
					iframeOnload(iframe, top_el);
				};
			}
		}
		function updateCrumb(obj) {
			var parents = $(obj).parents('ul.submenu');

			var firsturl = "javascript:window.location.reload();";
			var crumbText = '<li><i class="icon-home home-icon"></i> <a	href="'+ firsturl +'" target="main-content">首页</a> <span class="divider"> <i class="icon-angle-right "></i></span></li>';
			for (i = parents.length - 1; i >= 0; i--) {
				var link = $(parents[i]).prev('a.dropdown-toggle');
				var text = link.text();
				var href = link.attr('href');
				crumbText += '<span class="divider "> <i	class="icon-angle-right"></i>&nbsp;</span>';
			}
			crumbText += '<li>' + $(obj).text() + '</li>';
			$("#breadcrumb").html(crumbText);
		}
		$(document).ready(function() {
			$(".nav-list").on('click', '.menuItem', function() {
				$("ul li ul li:has(a)").removeClass("active");
				$(this).parent().addClass("active");
				var href = $(this).attr('href');
				if (href.indexOf("?") != -1) {
					href = href + "&currentFuncId=" + $(this).attr('id');
				} else {
					href = href + "?currentFuncId=" + $(this).attr('id');
				}
				console.log(ctx + href);
				createIframe(ctx + href, 'main-content');
				updateCrumb(this);
				return false;
			});
			createIframe(ctx + "/system/user_index.do", 'main-content');
		});
	</script>
</body>
</html>

