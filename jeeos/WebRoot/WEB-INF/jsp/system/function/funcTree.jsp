<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<!-- page specific plugin CSS -->
<link rel="stylesheet" href="${ctx}/js/zTree/3.4/zTreeStyle.css"
	type="text/css"></link>
<script type="text/javascript"
	src="${ctx}/js/zTree/3.4/jquery.ztree.all-3.4.min.js"></script>
<script type="text/javascript" src="${ctx}/js/zTree/zTreeExt.js"></script>
<!-- inline scripts related to this page -->

<script type="text/javascript">
	function loadIframe(iframeId, href) {
		if (iframeId != "" && href != "") {
			$("#" + iframeId).attr("src", ctx + href).attr("style", "width:100%;");
			$("#" + iframeId).load(function() {
				var mainheight = $(this).contents().find("body").height();
				$(this).height(mainheight);
			});
		}
	}

	var ctx = '${ctx}';
	var treeNodeArray = new Array();
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		}
	};

	$(function() {
		<c:forEach items="${funcList}" var="func">
		var pId = "${func.chFunc.id}";
		var id = "${func.id}";
		var sortNo = "${func.chFuncSortno}";
		var name = "${func.chFuncName}";
		var url = "/system/func_update.do?id=" + id;
		var click = "javascript:loadIframe('contentIframe','" + url + "')";
		var open = false;
		if (pId == "") {
			open = true;
		}
		var oneTreeNode = '{id:"' + id + '",pId:"' + pId + '",name:"' + name + '",click:"' + click + '", open:' + open + ', sortNo:"' + sortNo + '"}';
		treeNodeArray.push(oneTreeNode);
		</c:forEach>
		var zNodeStr = '[' + treeNodeArray.join(",") + ']';
		var zNodes = (new Function("return " + zNodeStr))();//;eval('(' + zNodeStr + ')'); 两种方式转化成json，选其一

		$.fn.zTree.init($("#tree"), setting, zNodes);
		<c:forEach items="${funcList}" var="func" end="0">
		loadIframe("contentIframe", "/system/func_update.do?id=${func.id}");
		</c:forEach>
	});
</script>

</head>
<body>
	<div class="page-content">
		<div class="row">
			<div class="col-xs-12" style="padding-left: 0;">
				<div class="col-sm-3 " style="padding-left: 0;">
					<div class="widget-box">
						<div class="widget-header widget-header-flat">
							<h4 class="smaller">菜单导航</h4>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<div class="row">
									<div class="col-sm-12">
										<ul id="tree" class="ztree"
											style="width:100%; overflow:auto;height: 100%;"></ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-sm-9" style="padding-left: 0;">
					<div class="row">
						<div style="height:500px; ">
							<iframe id="contentIframe"
								style="width:100%;height:100%;border: 0;" frameborder="0"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>