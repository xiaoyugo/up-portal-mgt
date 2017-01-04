var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var ctx = localObj.protocol + "//" + localObj.host + "/" + contextPath;

jQuery(function($) {
	// 当输入用户名和密码时消除提示信息
	$("#loginName,#password").focus(function() {
		$("#msg").hide();
		$("#submitBtn").text("登录");
		$(this).addClass("focus");
	}).blur(function() {
		$(this).removeClass("focus");
	});
});

// 登录
function submitLogin() {
	if ($("#loginName").val() == "" || $("#password").val() == "") {
		$("#msg").html("用户名或密码不能为空").show();
		return;
	}
	if ($("#loginName").val() && $("#loginName").val() != "" && $("#password").val() && $("#password").val() != "") {
		$("#submitBtn").text("登录中...").removeClass("btn-warning").attr("disabled", "disabled");
		$.post(ctx + "/login/login_login.do", {
			"loginName" : $("#loginName").val(),
			"password" : $("#password").val(),
		}, function(data) {
			loginSuccess(data);
		});
	}
}
// 登录成功
function loginSuccess(data) {
	var msg = data.msg;
	if (msg == "success") {
		window.location.href = ctx + "/system/layout_layout.do";
	} else {
		$("#msg").html(msg).show();
		$("#submitBtn").val("登录").addClass("btn-warning").removeAttr("disabled");
	}
}

$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		submitLogin();
	}
});
