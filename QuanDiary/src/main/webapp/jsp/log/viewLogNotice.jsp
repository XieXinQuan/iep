<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<script type="text/javascript">
			var id = ${id };

			$(function(){

				$.ajax({
					url:path+"/QuanDiary/log/viewLogNoticeById.do",
					type:"post",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						var notice = result.notice;
						$("#logNoticeTitle").text(notice.title);
						var html = "";
						html += "<b>通告内容：</b><br>";
						html += "<div style='background-color: #FFFFFF'>"+notice.content+"</div>";
						
						$("#logNoticeContent").html(html);
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
					<td>发布人：</td>
					<td>公司管理员</td>
					<td class="addTdWidth"></td>
					<td>标题：</td>
					<td><span id="logNoticeTitle"></span></td>
				</tr>
			</table>
			
			
			</div>
			
		</div>
		<div id="logNoticeContent" style="width:1180px;overflow-x: auto;">
			
			
		</div>

	</body>

</html>