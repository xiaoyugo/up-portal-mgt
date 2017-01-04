/* *
 * js工具函数库
 * By: wade
 * Version : 1.0
*/
(function($){
	
	//==========================================js全局函数==============================================
	
	//jquery内置行数： $.parseFloat
	$.extend({
		 /**
		  * 1、js i18n 国际化
		  */
		  i18n : function(key,params){
			   var result = key;
			   if(typeof(key) != 'undefined' && typeof(LocaleData) != 'undefined'){
				   //根据key取得对应的资源内容
				   if(LocaleData[key] != undefined){
					   result = LocaleData[key];
				   }
				   //替换对应参数
				   if(params){
					   for(var k in params){
						   result = result.replace("{"+k+"}",params[k]);
					   }
				   }
			    }
				return result;
		 },
		 
	    /**
	     * 2、浮点数校验
	     */
	     isNumber : function(num){
	    	  return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(num);
	     },
	     
	    /**
	     * 3、整数校验
	     */
	     isDigit : function(num){
	    	 return /^\d+$/.test(num);
	     },
	     
	    /**
	     * 4、email校验
	     */
	     isEmail : function(email){
	    	 return /^\b[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}\b$/i.test(email);
	     },
	     
	     /**
	     * 5、四舍五入，如$.roundHalfUp(2.3567,2)=2.36
	     */
	     roundHalfUp : function(num,pos){
	    	  return (Math.round(num*Math.pow(10, pos))/Math.pow(10, pos)).toFixed(pos);
	     },
	     
	    /**
	     * 6、判断数组是否已包含了某个元素
	     */
	     containObj : function(arr,obj){
	    	  var i = arr.length;
			  while (i--) {
				 if (arr[i] == obj) {
				     return true;
				 }
			 }
			 return false;
	     },
	     
	    /**
	     * 7、数组去重
	     * 如：[1,1,2,2,3,4] 转化后成为 [1,2,3,4]
	     */
	     uniqueArr : function(arr){
	    	  var arrtemp = [];
			  var j=0;
			  for(var i=0;i<arr.length;i++){
			     if($.inArray(arr[i],arrtemp)<0){
			         arrtemp[j] = arr[i];
			         j++;
			     }
			  }
			  return arrtemp;
	     },
	     
	    /**
	     * 8、载入iframe
	     */
	     loadIframe : function(iframeId,href){
	    	 if(iframeId!="" && href!=""){
				 $("#"+iframeId).attr("style","width:100%;height:99%;").attr("src",ctx+href);
		     }
	     },
	     
	    /**
	     * 9、把时间字符串转换成绝对时间
	     * datastr 时间格式为：2012-12-26 16:46:46
	     */
	     timeExchange : function(datastr){
	    	 var date = new Date();
			 var perfex = datastr.split(" ")[0];
			 var surfex = datastr.split(" ")[1]
			 if (perfex) {
			     var y = perfex.split("-")[0] / 1;
				 //因为月是从0开始的，所以减一
				 var m = perfex.split("-")[1] / 1-1;
				 var d = perfex.split("-")[2] / 1;
				 date.setFullYear(y, m, d);
			 }
			 if (surfex) {
				 var h = surfex.split(":")[0] / 1;
				 var m = surfex.split(":")[1] / 1;
				 var s = surfex.split(":")[2] / 1;
			     date.setHours(h, m, s, 0);
			 }
			 return date.getTime();
	     },
	     
	     /**
	     * 10、 返回两个Time相差的天数  
	     * 这里是计算天数,如果想获得周数则在该返回值上再除以7
	     */
	     differDayBetweenTwoTime : function(time1,time2) {
		     var differDay = $.fomatFloat(Math.abs($.timeExchange(time1)- $.timeExchange(time2))/1000/60/60/24,1);
		     return differDay;
	     },
	     
	     /**
		  * 11、URL加密
		  * $.URLEncode(url);
		  */
		 URLEncode:function(url){
			 var encodedUrl='';
			 var i=0;
			 var reg=/(^[a-zA-Z0-9_.]*)/;
		     while(i<url.length){
		    	 var m=reg.exec(url.substr(i));
			      if(m!=null && m.length>1 && m[1]!=''){
			      	  encodedUrl+=m[1];i+=m[1].length;
			      }else{
			         if(url[i]==' ')
			           encodedUrl+='+';
			         else{
			        	 var d=url.charCodeAt(i);
			        	 var h=d.toString(16);
			             encodedUrl+='%'+(h.length<2?'0':'')+h.toUpperCase();
			         }
			          i++;
			       }
	        }
	        return encodedUrl;
	    },
	    
	   /**
	    * 12、URL解密
	    * $.URLDecode(encodedUrl);
	    */
	    URLDecode:function(encodedUrl){
		   	var url=encodedUrl;
		   	var temp;
		   	var reg=/(%[^%]{2})/;
		    while((m=reg.exec(url))!=null && m.length>1 && m[1]!=''){
		    	b=parseInt(m[1].substr(1),16);
		        temp=String.fromCharCode(b);
		        url=url.replace(m[1],temp);
		    }
		    return url;
	    }
	    
	}),  
	
   //==============================================jquery 插件=================================================
   /***
	 * 1、数字校验
	 * $(".number").number();
	 * <input number="3"/> 三位整数
	 * <input number="3,2"/> 整数为3位，小数为2位
	 */
	 $.fn.number = function() {
		$(this).bind({
			  keyup:function(){checkNumber($(this))},
			  blur:function(){checkNumber($(this))}
		});
        return this;
    };
    
	/**
	 * 2、text、textarea、password，超出长度提示信息
	 */
	$.fn.lengthLimit = function(options) {
		var defaults = {     
		    msgTarget:""//自定义提示信息的显示位置，如$("textarea").lengthLimit({msgTarget:"#msgTip"});
		};     
		var opts = $.extend(defaults, options); 
		$(this).bind({
			  keyup:function(){checkLengthLimit($(this),opts);},
			  blur:function(){checkLengthLimit($(this),opts);}
		});
		return this.each(function() { 
			$(this).after("<span id='limit_msg_"+$(this).attr("id")+"_"+$(this).attr("name")+"'></span>");
			checkLengthLimit($(this),opts);
			return this;
	    });     
    };
    
   /**
    * 3、文本框文本域提示文字的自动显示与隐藏
    */
	$.fn.textRemindAuto = function(options){
		options = options || {};
		var defaults = {
			blurColor: "#999",
			focusColor: "#333",
			chgClass: "",
			passWordClass:""
		};
		var settings = $.extend(defaults,options);
		
		$(this).each(function(){
			var v = $.trim($(this).val());//值
			var title = $.trim($(this).attr("title"));//标题
			var regExp = $.trim($(this).attr("regExp"));//正则表达式
			if(v=="" && title!=""){
				$(this).val(title);
				$(this).css("color",settings.blurColor);
			}
			
			$(this).focus(function(){
				v = $.trim($(this).val());
				title = $.trim($(this).attr("title"));
				v = $.trim($(this).val());
				if(title!="" && title==v){
					$(this).val("");
				}
				$(this).css("color",settings.focusColor);
				if(settings.chgClass){
					$(this).toggleClass(settings.chgClass);
				}
			}).blur(function(){
				v = $.trim($(this).val());
				title = $.trim($(this).attr("title"));
				if(v=="" && title!=""){
					$(this).val(title);
					$(this).css("color",settings.blurColor);
					if(settings.chgClass){
						$(this).toggleClass(settings.chgClass);
					}
				}
			});
		});
	};
	
	/**
	 * 4、密码隐藏和显示
	 */
	$.fn.showHidePassword = function(options) {     
	    var defaults = {     
		    width: '148px',
		    height:'16px'
		};     
		var opts = $.extend(defaults, options); 
		return this.each(function() { 
			$(this).attr("width",opts.width).attr("height",opts.height);
		    var obj = $(this)[0];
	    	if(obj.type.toLowerCase()=="password"){//toUpperCase()
	    		 obj.type="text";
	    	}else{
	    		 obj.type="password";
	    	}
	    });     
	};  
	
	/**
	 * 5、table 列表页面中，checkbox 全选与取消全选
	 * $("#allCheck").toggleAllCheckbox({checkName: 'ids'});
	 */
	$.fn.toggleAllCheckbox = function(options) {   
		 var defaults = {     
		    checkName: 'ids' /**checkbox属性的名称，默认为ids*/
		};   
		var opts = $.extend(defaults, options); 
		return this.click(function() { 
			  if($(this)[0].checked){
			      $("input[name='"+opts.checkName+"']").each(function() { 
					  $(this).attr("checked", true); 
				  }); 
			   }else{
				   $("input[name='"+opts.checkName+"']").each(function() { 
					   $(this).attr("checked", false); 
				  }); 
			   }
			  return this;
	     });     
	};         
	
	/**
	 * 6、table 隔行换色
	 * $(".ltable tr:even").changeEvenTrColor();
	 */
	$.fn.changeEvenTrColor = function() {   
		return this.each(function() { 
			 $(this).addClass("ltablehover");
	    });     
	}; 
	
	/**
	 * 6、table 点击换色
	 * $(".ltable tr").clickTrColor();
	 */
	$.fn.clickTrColor = function() {   
		return this.click(function() { 
			  $(this).siblings().removeClass('ltableclick'); 
		      $(this).addClass('ltableclick'); 
	    });     
	}; 
	
	/**
	 * 7、清空验证消息
	 * $(":input[type='text'],:input[type='password'],select,textarea").clearValideMsg();
	 */
	$.fn.clearValideMsg = function() {   
		return this.focus(function() { 
		    var labelId = $(this).attr("id");
		    if(typeof(labelId)=="undefined"){labelId = $(this).attr("name");}
		    $("label[for='"+labelId+"']").html("");
	    });     
	}; 
	
	//========================================以下是私有函数=============================================
	/**
    * 数字校验
    */
   function checkNumber(obj) {  	
	     var val = obj.val().replace("/,/g","");
	     try {
	    	  //if(!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val) && !/^\d+$/.test(val)) {
	    	   if(isNaN(val)) {
	    		   
	    		  obj.val("");
	    	      return;
	    	   }
				var number = obj.attr("number");
				var scaleLength; //小数部分位数
				var intLength; //整数位数
				if(number.split(",").length == 2) {
					scaleLength = parseInt(number.split(",")[1]);
				}
				intLength = parseInt(number.split(",")[0]);
				var f1;
				var i = val.indexOf(".");
				if((i==-1?val.length:val.substring(0,i).length-1) > intLength) {
					//"数值溢出"
					var tempval=val.replace(",","");
					var newval=tempval.substring(0,intLength);
					obj.val(newval);
				}
				//小数舍入开始
				else if(i != - 1) {
					if(val.substring(i+1).length > scaleLength) {
						f1 = "0" + val.substring(i);
						f1 = $.parseFloat(f1);
						f1 = Math.round(f1*Math.pow(10,scaleLength))/Math.pow(10,scaleLength)+"";
						var newval = val.substring(0,i) + f1.substring(1);
						if(newval.indexOf(".") == -1) {
							newval+=".00"
						}
						obj.val(newval);
						 //"数值舍入"
					}
				}
				//小数舍入结束
			 } catch(e) {
				 alert(e);
			}
         return this;
    }
   
   /**
    * text、textarea、password，长度校验
    */
   	function checkLengthLimit(obj,opts){ 
			var msgTarget = opts.msgTarget;
			var val = obj.val();
			var maxlength = obj.attr("maxlength");
			if(typeof(maxlength)!=="undefined"){
				var length = val.length;
				if(length > maxlength){
					obj.val(obj.val().substring(0,maxlength));
				}
				var id = obj.attr("id")+"_"+obj.attr("name");
				var limitMsg = "剩余"+((maxlength-length<=0)?'0':maxlength-length)+"字";
				if(msgTarget!=""){
					$(msgTarget).html(limitMsg);
				}else{
					$("#limit_msg_"+id).html(limitMsg);
				}
			}
		}
})(jQuery);

