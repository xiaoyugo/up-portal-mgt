count=1;
	
	// 表格后插入一行表格
	function addOneEpisode(){
		if (count > 1) {// 当表格中已有一行时候验证前一行的内容，如果为空不允许添加
				var url = $("#ct_" + (count - 1)).val();
				if (url.replace(/(^\s*)|(\s*$)/, "") == "") // 去空格操作
				{
				bootbox.dialog({
					message: "<span class='bigger-110'>请完整填写当前内容后再添加！</span>",
						buttons: 			
						{
							"ok" :
							 {
								"label" : "<i class='icon-ok'></i>确定!",
								"className" : "btn-sm btn-success",
								"callback": function() {
									
								}
							}
						}
					});
			
					return;
				}
			}
			var options = document.getElementById("resSel").innerHTML;
			var html = '<tr id="${content.id }" class="episode">'
					+ '<td class="center">'
					+ '<input type="checkbox" class="ace"'
													+'	id="ids" name="ids" value="" /><span'
													+'	class="lbl"></span>'
					+ '	</td>'
					+ '<td>'
					+ '<select id="sel_'+count+'" class="width-100">'
					+ '<option id="8a83a2744a17fdef014a1808876b0001">默认来源</option>'
					+ '</select>'
					+ '</td>'
					+ '	<td>'
					+ '	<input type="text" name="contUrl" id="ct_'+count+'"'
												+'		class="col-xs-12 col-sm-12" value="" />'
					+ '	</td>' + '	<td>' + '<a href="#" class="green btn btn-success  btn-xs radius-4" id="a_'
					+ count + '" onclick="saveURL(' + count
					+ ')"><i class="icon-save"></i></a>' + '	</td></tr>';
			$("#userTbody").append(html);
			count++;

		}
		// 保存一集
		function saveURL(index) {
		if($("#ct_" + index).val().replace(/(^\s*)|(\s*$)/, "") == "")
		{
		$.gritter.add({
			  title : "",
			  text : "请完善内容后再保存！",
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
			$.post("${ctx}/xbrowser/movie_addOneMovURl.do",
				{
				resName: $("#sel_" + index).val(),
				url: $("#ct_" + index).val(),
				id:$("#contId").val(),
			}, function(data) {
				$.gritter.add({
			  title : "",
			  text : "添加成功!",
			  sticky : false,
			  before_open : function() {
				  if ($('.gritter-item-wrapper').length >= 3) {
					  return false;
				  }
			  },
			  class_name : 'gritter-success'
			});
			});
		}
		// 更新
		$(function() {
			$(".episode").each(
					function() {
						var tmp = $(this).children().eq(3);
						var btn = tmp.children();
						$("#update").on("click", function() {
							var urlId = btn.parent().parent()
									.children("td").get(0).children[0].value;
							var resId = btn.parent().parent().children("td")
									.get(1).children[0].id;
							var url = btn.parent().parent().children("td").get(
									2).children[0].value;
									if(url.replace(/(^\s*)|(\s*$)/, "") == "")
		{
		$.gritter.add({
			  title : "",
			  text : "请完善内容后再保存！",
			  sticky : false,
			  before_open : function() {
				  if ($('.gritter-item-wrapper').length >= 3) {
					  return false;
				  }
			  },
			  class_name : 'gritter-error '
			});
		 return;
		}
							$.ajax({
								type : "post",
								datatype:"json",
								url : "${ctx}/xbrowser/movie_updateOneMovURl.do",
								data : {
								"urlId":urlId,
								"resId":resId,
								"url":url,
								"id":$("#contId").val()
								},
								success : function(msg) {
									$.gritter.add({
			                               title : "",
			                               text : "更新成功!",
			                               sticky : false,
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-success'
			                                });
								},
								error : function() {
									$.gritter.add({
			                               title : "",
			                               text : "更新失败!",
			                               sticky : false,
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
									return;
								},
							});
						});
					});
		});
		// 删除
		function ajaxDelete() {
			var ids = "";
			$('input[type="checkbox"][name="ids"]:checked').each(function() {
				ids += "," + $(this).val();
			});
			if (ids == "") {
				$.gritter.add({
			                               title : "",
			                               text : "您未选择任何数据，系统已放弃本次操作！",
			                               sticky : false,
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
				return;
			}
			if (ids.indexOf(",") == 0) {
				ids = ids.substring(1);
			}
			bootbox.dialog({
					message: "<span class='bigger-110'>您确定要删除选中数据吗？</span>",
						buttons: 			
						{
						"cancel" :
							 {
								"label" : "<i class='icon-remove'></i>取消",
								"className" : "btn-sm btn-info",
								"callback": function() {
								}
							},
							"ok" :
							 {
								"label" : "<i class='icon-ok'></i>确定!",
								"className" : "btn-sm btn-success",
								"callback": function()
                                  {
									$.ajax({
									type : "post",
								    datatype:"json",
									url:"${ctx}/xbrowser/movie_deleteUrlByIds.do",
									data:{
									"ids":ids
									},
					                error : function() {
					                $.gritter.add({
			                               title : "",
			                               text : "系统异常，删除失败!",
			                               before_open : function() {
				                           if ($('.gritter-item-wrapper').length >=1) {
					                            return false;
				                            }
			                                },
			                                  class_name : 'gritter-error'
			                                });
					                      },
					                  success:function()
					                  {
					                  bootbox.dialog({
					message: "<span class='bigger-110'>删除成功！</span>",
						buttons: 			
						{
							"ok" :
							 {
								"label" : "<i class='icon-ok'></i>确定!",
								"className" : "btn-sm btn-success",
								"callback": function() {
									location.reload();
								}
							}
						}
					});
					                  }
									});
								}
							}
							
						}
					});
		}
		/* 多选框选级联选中操作 */
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