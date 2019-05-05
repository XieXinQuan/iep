<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	
</style>
<script type="text/javascript">
	$(function(){
		var loginName = "${loginName }";
		$("#loginName").val(loginName);
		
	});
	function confirmSumbit(){
		if(checkVal($("#oldPassword").val(), "原密码")) return;
		if(checkVal($("#newPassword").val(), "新密码")) return;
		if(checkVal($("#affirmNewPassword").val(), "确认新密码")) return;
		if($("#oldPassword").val().length < 6 || $("#oldPassword").val() > 18){
			alert("密码长度在6-18位数之间！");
			return;
		}
		if($("#oldPassword").val() == $("#newPassword").val()){
			alert("新密码与旧密码相同！");
			return;
		}
		if($("#affirmNewPassword").val() != $("#newPassword").val()){
			alert("两次密码不一样！");
			return;
		}
		$.ajax({

			url:path+"/QuanDiary/user/userChangePasswordById.do",
			type:"post",
			data:{"oldPassword":$("#oldPassword").val(), "newPassword":$("#newPassword").val()},
			dataType:"json",
			success:function(result){
				if(result.status == 88){
					alert(result.msg+",即将返回登录界面！");
					setTimeout("window.location='../login/login1.html';", 1000);
				}else{
					alert(result.msg);
				}
			}
		});
	}
	function checkVal(val,text){
		var isReturn = false;
		if(val == null || val == ""){
			alert(text+"不能为空！");
			isReturn = true;

		}
		return isReturn;
	}
</script>
</head>
<body>
		<table>
			<tr>
				<td style="width:100px;">账&nbsp;&nbsp;&nbsp;号：</td>
				<td><input id="loginName" readonly="readonly"></td>
				
			</tr>
			<tr>
				<td>原&nbsp;密&nbsp;码：</td>
				<td><input id="oldPassword" type="password" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></td>

			</tr>
			<tr>
				<td>新&nbsp;密&nbsp;码：</td>
				<td><input id="newPassword" type="password" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></td>

			</tr>
			<tr>
				<td>确认新密码：</td>
				<td>
					<input id="affirmNewPassword" type="password" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')">
				</td>

			</tr>
			<tr style="height:50px;">
				<td colspan="2" ><div style="width:100%;height:100%;text-align:center;"><button class='handle' type="button" onclick="confirmSumbit();">提交</button></div></td>

			</tr>
		</table>
</body>
</html>