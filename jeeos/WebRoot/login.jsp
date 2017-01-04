<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="<%=request.getContextPath() %>" />
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("loginName");
	String password = request.getParameter("password");
	// 得到所有的Cookie
	Cookie cookies[] = request.getCookies();
	/* for (Cookie c : cookies) {
		System.out.println("-c-" + c.getName());
	} */
	// 如果是上一步提交过来的，则更新Cookie的值
	if (name != null) {
		Cookie c = new Cookie("loginName", name);
		c.setMaxAge(30); // Cookie 的有效期为 30 秒
		response.addCookie(c);
	} else if (cookies != null) {
		// 如果已经设置了cookie ， 则得到它的值
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("loginName"))
				name = cookies[i].getValue();
		}
	}
	if (password != null) {
		Cookie c = new Cookie("password", password);
		c.setMaxAge(30); // Cookie 的有效期为 30 秒
		response.addCookie(c);
	} else if (cookies != null) {
		// 如果已经设置了cookie ， 则得到它的值
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("password"))
				password = cookies[i].getValue();
			System.out.println("--" + password);
		}
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>统一运营管理平台</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/css/login.css" rel="stylesheet" />
</head>

<body>

	<div id="login-container">
		<%-- <img class="img" src="${ctx}/images/colorblock.jpg" /> --%>
		<div id="login-title">
			<h3>统一运营管理平台</h3>
		</div>
		<!--  /login-header -->
		<div id="login-content" class="clearfix">
			<fieldset>
				<div class="control-group username-row">
					<div class="controls">
						<input type="text" name="loginName" id="loginName" maxlength="50"
							placeholder="username"
							value="<%if (name != null)
				out.println(name);%>" />
					</div>
				</div>
				<div class="control-group password-row">
					<div class="controls">
						<input type="password" id="password" name="password"
							maxlength="50" placeholder="password"
							value="<%if (password != null)
				out.println(password);%>"
							onkeydown="if(event.keyCode==13||event.which==13)submitLogin();" />
					</div>
				</div>
				<div class="pull-right" style="">
					<label>记住密码<input name="remember" type="checkbox" value="${remember}" />
					</label>
				</div>
				<!-- 登录提示信息 -->
				<div>
					<label class="control-label errtips" for="msg" id="msg">
						${loginMessage} </label>
				</div>
			</fieldset>
			<div class="pull-right">
				<button id="submitBtn" onclick="submitLogin()"
					class="btn btn-success btn-large">登录</button>
			</div>
		</div>
		<!-- /login-content -->
		<div id="login-extra">
			<p>【推荐使用Chrome内核浏览器或ie9以上版本的浏览器】</p>
		</div>
		<!-- /login-extra -->
	</div>
	<!-- /login-wrapper -->

	<!-- page javascript
================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/js4page/login.js"></script>

</body>
</html>
