function clean() {
	var myDate = getNowFormatDate();
	if ($("#weekOrMonth").val().trim() == 'monthly') {
		$("#beginTime").val("2014-01");
		$("#endTime").val(myDate.substring(0, 7));

		$("#tempBegin").attr("value", "/20141");
		$("#tempEnd").attr("value", "/" + getFormatTempDate());

	}
	if ($("#weekOrMonth").val().trim() == 'weekly') {
		$("#beginTime").val("2014-01-01");
		$("#endTime").val(myDate.toString());

		$("#tempBegin").attr("value", "/" + getWeekNumber("2014-01-01"));
		$("#tempEnd").attr("value", "/" + getWeekNumber(myDate.toString()));
	}

}
function weekOrMonthBeginTime() {
	if ($("#weekOrMonth").val().trim() == 'monthly') {
		WdatePicker({
			minDate : '2014-01',
			maxDate : '#F{$dp.$D(\'endTime\')||\'%y-%M\'}',
			skin : 'blueFresh',
			dateFmt : 'yyyy-MM',
			startDate : '2014-01-01',
			readOnly : true
		});
	}
	// 按周统计
	if ($("#weekOrMonth").val().trim() == 'weekly') {
		WdatePicker({
			minDate : '2013-12-30',
			maxDate : '#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',
			skin : 'blueFresh',
			startDate : '2014-01-01',
			isShowWeek : true,
			firstDayOfWeek : 1,
			disabledDays : [ 1, 2, 3, 4, 5, 6 ],
			readOnly : true
		});
	}
	pickedFunc("tempBegin");
}

function weekOrMonthEndTime() {
	// 按月统计
	if ($("#weekOrMonth").val().trim() == 'monthly') {
		WdatePicker({
			minDate : '#F{$dp.$D(\'beginTime\')||\'2014-01\'}',
			skin : 'blueFresh',
			dateFmt : 'yyyy-MM',
			maxDate : '%y-{%M-1}',
			readOnly : true
		});
	}

	// 按周统计
	if ($("#weekOrMonth").val().trim() == 'weekly') {
		WdatePicker({
			minDate : '#F{$dp.$D(\'beginTime\')||\'2013-12-30\'}',
			maxDate : '%y-%M-%d',
			skin : 'blueFresh',
			isShowWeek : true,
			firstDayOfWeek : 1,
			disabledDays : [ 0, 1, 2, 3, 4, 5 ],
			readOnly : true
		});
	}
	pickedFunc("tempEnd");
}

function pickedFunc(timeType) {
	// 按月统计
	if ($("#weekOrMonth").val().trim() == 'monthly') {
		if ($dp.cal) {
			var temp = $dp.cal.getDateStr('yyyyM');
			$("#" + timeType).attr("value", "/" + temp);
		}

	}

	// 按周统计
	if ($("#weekOrMonth").val().trim() == 'weekly') {
		if ($dp.cal) {
			var temp = $dp.cal.getDateStr('yyyyW');
			$("#" + timeType).attr("value", "/" + temp);
		}

	}
}

function showLoding(myChart, showText) {
	myChart.showLoading({
		text : showText,
		effect : 'bubble',
		textStyle : {
			fontSize : 20
		}
	});
}

function getNowFormatDate() {
	var day = new Date();
	var Year = 0;
	var Month = 0;
	var Day = 0;
	var CurrentDate = "";
	// 初始化时间
	// Year= day.getYear();//有火狐下2008年显示108的bug
	Year = day.getFullYear();// ie火狐下都可以
	Month = day.getMonth() + 1;
	Day = day.getDate();
	// Hour = day.getHours();
	// Minute = day.getMinutes();
	// Second = day.getSeconds();
	CurrentDate += Year + "-";
	if (Month >= 10) {
		CurrentDate += Month + "-";
	} else {
		CurrentDate += "0" + Month + "-";
	}
	if (Day >= 10) {
		CurrentDate += Day;
	} else {
		CurrentDate += "0" + Day;
	}
	return CurrentDate;
}

// 传给接口月时间
function getFormatTempDate() {
	var date = new Date();
	// 初始化时间
	// Year= day.getYear();//有火狐下2008年显示108的bug
	var year = date.getFullYear();// ie火狐下都可以
	var month = date.getMonth() + 1;
	day = date.getDate();
	// Hour = day.getHours();
	// Minute = day.getMinutes();
	// Second = day.getSeconds();
	return year + "" + month;
}

// 传给接口周时间
function getWeekNumber(d) {
	// Copy date so don't modify original
	d = new Date(d);
	d.setHours(0, 0, 0);
	// Set to nearest Thursday: current date + 4 - current day number
	// Make Sunday's day number 7
	d.setDate(d.getDate() + 4 - (d.getDay() || 7));
	// Get first day of year
	var yearStart = new Date(d.getFullYear(), 0, 1);
	// Calculate full weeks to nearest Thursday
	var weekNo = Math.ceil((((d - yearStart) / 86400000) + 1) / 7)
	// Return array of year and week number
	var getFullYear = d.getFullYear();
	return getFullYear + "" + weekNo;
}