function setCookie(name,value,day){  
	var exp  = new Date();  //获得当前时间
	exp.setTime(exp.getTime() + day*24*60*60*1000);  //换成毫秒
	document.cookie = name + "="+ value + ";expires=" + exp.toGMTString();
}   

function getCookie(name) {
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null; 
}