/* *
 * 页面包含crud操作时，初始化就加载的js函数
 * By: wade
 * Version : 1.0
*/
$(function() {
	//=============================新增修改页面js===========================================
	//删除数据，同时删除ztree节点
	if(typeof(deleteAndRemoveTreeNode)!="undefined" && deleteAndRemoveTreeNode){
		$(".deleteOne").deleteOne({
			url:deleteurl,
			onComplete:function(param){
			     parent.removeNodes(param);
			}
		});
		$("#batchDelete").batchDelete({
		    url:deleteurl,
		    onComplete:function(param){
				parent.removeNodes(param);
			}
		 });
	}else if(typeof(deleteurl)!="undefined"){//删除数据
		$(".deleteOne").deleteOne({url:deleteurl});
	    $("#batchDelete").batchDelete({url:deleteurl});
	}
});