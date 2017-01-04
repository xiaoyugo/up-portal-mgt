function sAlert(msg, callback) {
	if ($.dialog) {
		$.dialog.alert(msg, function() {
			if(msg.trim() == "删除成功"){
				window.location = window.location.href;
			}
			if ($.isFunction(callback)) {
				return callback();
			}
		});
	} else {
		alert(msg);
		
	}
	
}

function sConfirm(msg, callback1, callback2) {
	if ($.dialog) {
		$.dialog.confirm(msg, function() {
			if ($.isFunction(callback1)) {
				return callback1();
			}
		}, function() {
			if ($.isFunction(callback2)) {
				return callback2();
			}
		});
	}else{
		console.log('$.dialog is not defined.')
	}
}