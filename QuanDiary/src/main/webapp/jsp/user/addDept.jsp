<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	
</style>
<script type="text/javascript">
	var deptId = ${deptId };
	
	function sumbitDeptName(){
		var deptName = $("#addDeptName").val();
		if(deptName == null || deptName == ""){
			alert("请输入部门名称！");
			return;
		}
		$.ajax({

			url:path+"/QuanDiary/company/saveDept.do",
			type:"post",
			data:{"deptId":deptId,"deptName":deptName},
			dataType:"json",
			success:function(result){
				if(result.status == 1){
					alert("添加成功！");
					closeWindow();
				}else{
					alert(result.msg);
				}
			}
		});
	}
</script>
</head>
<body>
	<div>
		
		<b>部门名称：</b><input type="text" id="addDeptName" placeholder="请输入部门名称">
		<br><input onclick="sumbitDeptName();" type="button" value="提交">
	</div>
</body>
</html>