var api = frameElement.api, W = api.opener;
var tolisturl = "${ctx}/xbrowser/movie_list.do";

$(document).ready(function() {
	// 浏览图片文件事件
	$("#BimgScan").click(function() {
		$("#BimgFileField").click()
	});
	$("#SimgScan").click(function() {
		$("#SimgFileField").click()
	});
	/*// 排序单选按钮组事件
	$("#RadioGroup1_0").change(function() {
		$("#rank").attr("disabled", "disabled");
	});
	$("#RadioGroup1_1").change(function() {
		$("#rank").removeAttr("disabled");
	});*/

	// 排序输入改变验证事件
	$("#rank").change(function() {
		var val = $("#rank").val();
		if (val <= 0)
			$("#rank").val(1);
	});
});

jQuery(function($) {
	$(".select2").css('width', '300px').select2({
		allowClear : true
	}).on('change', function() {
		$(this).closest('form').validate().element($(this));
	});

	$("#submitBtn").on('click', function() {
		if (!$('#validation-form').valid()) {
			return false;
		}
	});

	$("#validation-form")
			.validate(
					{
						errorElement : 'div',
						errorClass : 'help-block',
						focusInvalid : false,
						rules : {
							contentName : {
								required : true,
								maxlength : 50
							},
						},

						messages : {
							contentName : {
								required : "请输入电影名称！"
							},
						},

						highlight : function(e) {
							$(e).closest('.form-group').removeClass('has-info')
									.addClass('has-error');
						},

						success : function(e) {
							$(e).closest('.form-group')
									.removeClass('has-error').addClass(
											'has-info');
							$(e).remove();
						},

						errorPlacement : function(error, element) {
							if (element.is(':checkbox') || element.is(':radio')) {
								var controls = element
										.closest('div[class*="col-"]');
								if (controls.find(':checkbox,:radio').length > 1)
									controls.append(error);
								else
									error.insertAfter(element.nextAll(
											'.lbl:eq(0)').eq(0));
							} else
								error.insertAfter(element.parent());
						},

						submitHandler : function(form) {
							var contentname = document.getElementById("contentName").value;
							var starring = $("#starring").val();
							var director = $("#director").val();
							var introduction = $("#introduction").val();
							var type = $("#type").val();
							var year = $("#year").val();
							var region = $("#region").val();
							var memo = $("#memo").val();
							var rank = $("#rank").val();
							$.ajax({
										url : "${ctx}/xbrowser/movie_findMovieByName.do?contentname="
												+ contentname,
										type : 'post',
										dataType : "json",
										error : function() {
											$.gritter
													.add({
														title : "系统提示",
														text : "查找电影信息失败！",
														sticky : false,
														before_open : function() {
															if ($('.gritter-item-wrapper').length >= 1) {
																return false;
															}
														},
														class_name : 'gritter-error  '
													});
											return;
										},
										success : function(data) {
											if (data != null && data.length > 0) {
												$.gritter
														.add({
															title : "系统提示",
															text : "该电影已在系统中，系统放弃本次保存操作！",
															sticky : false,
															before_open : function() {
																if ($('.gritter-item-wrapper').length >= 1) {
																	return false;
																}
															},
															class_name : 'gritter-error '
														});
												return;
											} else {
												$.ajax({
															url : "${ctx}/xbrowser/movie_findMovieByRank.do",
															type : 'post',
															dataType : "json",
															data : {
																"rank" : rank,
																"catId":"00bd95fd680b11e4ae34f8a963a23170"
															},
															success : function(
																	data) {
																if (data != null
																		&& data.length > 0) {
																	$.gritter
																			.add({
																				title : "系统提示",
																				text : "已有电影排名与本电影冲突，系统已放弃本次保存操作。请修改排名后再保存！",
																				sticky : false,
																				before_open : function() {
																					if ($('.gritter-item-wrapper').length >= 1) {
																						return false;
																					}
																				},
																				class_name : 'gritter-error '
																			});

																	return;
																} else {
																	$(form)
																			.ajaxSubmit(
																					{
																						type : "post",
																						url : "${ctx}/xbrowser/movie_add.do",
																						data : {
																							"contentname" : contentname,
																							"starring " : starring,
																							"director " : director,
																							"introduction" : introduction,
																							"type" : type,
																							"year " : year,
																							"region" : region,
																							"memo " : memo,
																							"rank" : rank
																						},
																						error : function(
																								data) {
																							alert(data);
																							console
																									.log(data.innerHTML);
																							return;
																						},
																						success : function() {
																							$.gritter
																									.add({
																										title : "系统提示",
																										text : "保存成功！",
																										sticky : false,
																										before_open : function() {
																											if ($('.gritter-item-wrapper').length >= 1) {
																												return false;
																											}
																										},
																										class_name : 'gritter-success '
																									});
																							setTimeout("api.reload(W,tolisturl)", 1000);
																						}
																					});

																}
															}
														});
											}
										}

									});
						},
						invalidHandler : function(form) {
						}
					});
});