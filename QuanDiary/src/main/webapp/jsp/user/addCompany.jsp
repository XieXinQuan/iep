<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	function confirmSumbit(){
		

		var companyName = $("#companyName").val();
		var companyShortName = $("#companyShortName").val();
		var companyManagerLoginName = $("#companyManagerLoginName").val();
		var companyManagerDisplayName = $("#companyManagerDisplayName").val();
		
		if(companyInforNoNull(companyName, "公司名称")) return;
		if(companyInforNoNull(companyShortName, "公司简称")) return;
		if(companyInforNoNull(companyManagerLoginName, "管理员账号")) return;
		if(companyInforNoNull(companyManagerDisplayName, "管理员姓名")) return;

		$.ajax({

			url:path+"/QuanDiary/company/addCompanyByAdmin.do",
			type:"post",
			data:{"name":companyName, "shortName":companyShortName, 
				"loginName":companyManagerLoginName, "displayName":companyManagerDisplayName},
			dataType:"json",
			success:function(result){
				alert(result.msg);
			}
		});

	}
	function companyInforNoNull(val, text){
		
		if(val == null || val == ""){
			alert(text+"不能为空！");
			return true;
		}
		return false;
	}
	function checkLoginName(){
		var companyManagerLoginName = $("#companyManagerLoginName").val();
		if(companyManagerLoginName != null && companyManagerLoginName != ""){
			$.ajax({

				url:path+"/QuanDiary/user/checkLoginName.do",
				type:"post",
				data:{"loginName":companyManagerLoginName},
				dataType:"json",
				success:function(result){
					if(result.status == 1){
						$("#checkLoginNameMsg").html("<span style='color:green'>"+result.msg+"</span>");
					}else{
						$("#checkLoginNameMsg").html("<span style='color:red'>"+result.msg+"</span>");
					}
					
				}
			});
			
		}

	}
</script>
</head>
<body>
		<div>
		<table>
			<tr>
				<td style="width:100px;">公司全称：</td>
				<td><input id="companyName" type="text" style="width:200px;"></td>
				
			</tr>
			<tr>
				<td style="width:100px;">公司简称：</td>
				<td><input id="companyShortName" type="text" style="width:200px;"></td>
				
			</tr>
			<tr>
				<td>管理员账号：</td>
				<td><input id="companyManagerLoginName" onchange="checkLoginName();" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" style="width:200px;"></td>
				<td id="checkLoginNameMsg"></td>
			</tr>
			<tr>
				<td>管理员姓名：</td>
				<td><input id="companyManagerDisplayName" type="text" style="width:200px;"></td>
				<td id="checkLoginNameMsg"></td>
			</tr>
			<tr>
				<td>密&nbsp&nbsp码：</td>
				<td><input readonly="readonly" placeholder="初始密码默认为：123456" style="width:200px;"></td>
				<td></td>
			</tr>

			<tr style="height:50px;">
				<td colspan="2" ><div style="width:100%;height:100%;text-align:center;"><button class='handle' type="button" onclick="confirmSumbit();">提交</button></div></td>
				<td></td>
			</tr>
		</table>
		
	<div>


</body>
</html>