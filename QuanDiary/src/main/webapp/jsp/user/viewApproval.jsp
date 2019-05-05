<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<script type="text/javascript">
			var id = "${id }";

			$(function(){
				
				$.ajax({
					url:path+"/QuanDiary/company/viewApprovalById.do",
					type:"post",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						$("#appAuthorName").text(result.userName);
						$("#appAuthorTypeName").text(result.typeName);
						var a = result.approval;
						var html = "";
						if(a.type == 13 || a.type == 14){
							html += "<b>开始时间：</b><span>"+formatTime(a.start_time)+"</span><hr>";
							html += "<b>结束时间：</b><span>"+formatTime(a.end_time)+"</span><hr>";
							
						}else if(a.type == 11){
							html += "<b>离司日期：</b><span>"+formatDate(a.start_time)+"</span><hr>";
						}
						html += "<b>申请理由：</b><span>"+a.reason+"</span><br>";
						$("#appContent").append(html);
					}
				});
			});
		</script>
	</head>
	<body>
		<div id="diaryAuthorInfor" style="background-color: #DDA0DD">
			<div>
			<table style="margin-left: 380px;">
				<tr>
					<td>作者：</td>
					<td><span id="appAuthorName"></span></td>
					<td class="addTdWidth"></td>
					<td>类型：</td>
					<td><span id="appAuthorTypeName"></span></td>
				</tr>
			</table>
			
			
			</div>
			
		</div>
		<div id="appContent" style="width:100%;">
			
			
		</div>

	</body>

</html>