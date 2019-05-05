<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">

</style>
<script type="text/javascript">

	var uId = ${uId };
	$(function(){
		$.ajax({
			url:path+"/QuanDiary/company/loadUserInforByUserId.do",
			type:"post",
			data:{"id":id},
			dataType:"json",
			success:function(result){
				$("#aUserName").val(result.userName);
				if(result.pi != null){
					var pi = result.pi;
					$("#aAge").val(pi.age);
					$("#aSex").val(pi.sex);
					$("#aCity").val(pi.city);
					$("#aSemocratic").val(pi.democratic);
					$("#aIdentityCard").val(pi.identity_card);
					$("#aPhone").val(pi.phone);
					$("#aAdress").val(pi.adress);
					$("#aHeight").val(pi.height);
					$("#aWeight").val(pi.weight);
					
				}
			}
		});
	});
	function sumbitUserInfor(){
		
		if(checkVal($("#aUserName").val(),"名字")) return;
		if(checkVal($("#aSex").val(),"性别")) return;	
		if(checkVal($("#aCity").val(),"籍贯")) return;
		if(checkVal($("#aSemocratic").val(),"民族")) return;
		if(checkVal($("#aAge").val(),"年龄")) return;
		if(checkVal($("#aIdentityCard").val(),"身份证")) return;
		var aSex = $("#aSex").val();
		var aUserName = $("#aUserName").val();
		
		var aCity = $("#aCity").val();
		var aSemocratic = $("#aSemocratic").val();
		var aAge = $("#aAge").val();
		var aIdentityCard = $("#aIdentityCard").val();
		var aPhone = $("#aPhone").val();
		var aAdress = $("#aAdress").val();
		var aHeight = $("#aHeight").val();
		var aWeight = $("#aWeight").val();

		
		$.ajax({
			url:path+"/QuanDiary/company/saveEmployeeInformation.do",
			type:"post",
			data:{"uId":uId,"aUserName":aUserName,"sex":aSex,"city":aCity,"democratic":aSemocratic,
				"age":aAge,"identity_card":aIdentityCard,"phone":aPhone,"adress":aAdress,
				"height":aHeight,"weight":aWeight},
			dataType:"json",
			success:function(result){
				if(result.status == 1){
					alert("保存成功！");
					loadUserInfor(uId);
					closeWindow();
				}else{
					alert("保存失败！");
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

	<div>
	<br>
		<div><span>【基本信息】</span></div><hr>
		<table style="margin-left: 50px;">
			<tr>
				<td align='right'>姓名：</td>
				<td><input id="aUserName" type="text"></td>
				<td class="addTdWidth"></td>
				<td align='right'>性别：</td>
				<td><input id="aSex" type="text"></td>
			</tr>
			<tr>
				<td align='right'>籍贯：</td>
				<td><input id="aCity" type="text"></td>
				<td class="addTdWidth"></td>
				<td align='right'>民族：</td>
				<td><input id="aSemocratic" type="text"></td>
			</tr>

		</table>
			<br>
	<div><span>【个人信息】</span></div>
	<hr>
		<table style="margin-left: 50px;">
					<tr>
				<td align='right'>年龄：</td>
				<td><input id="aAge" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
				<td class="addTdWidth"></td>
				<td align='right'>身份证：</td>
				<td><input id="aIdentityCard" type="text" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></td>
			</tr>
			<tr>
				<td align='right'>手机号码：</td>
				<td><input id="aPhone" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"></td>
				<td class="addTdWidth"></td>
				<td align='right'>家庭住址：</td>
				<td><textarea id="aAdress" rows="1" cols="22"></textarea></td>
			</tr>
			<tr>
				<td align='right'>身高：</td>
				<td><input id="aHeight" type="text" onkeyup="if(isNaN(value))execCommand('undo')"></td>
				<td class="addTdWidth"></td>
				<td align='right'>体重：</td>
				<td><input id="aWeight" type="text" onkeyup="if(isNaN(value))execCommand('undo')"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td class="addTdWidth" align="center"><input class='handle' type="button" onclick="sumbitUserInfor();" value="确定"></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		


	</div>
	
</body>
</html>