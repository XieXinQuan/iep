<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<script type="text/javascript">
			var id = ${id };
			var title = "${title }";
			var author = "${userName }";
			$(function(){
				$("#diaryAuthorName").text(author);
				$("#diaryAuthorTitle").text(title);
				//alert(id);
				$.ajax({
					url:path+"/QuanDiary/log/viewDiaryById.do",
					type:"post",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						var content = result.content;
						//alert(content);
						var html = "";
						for(var i=0;i<content.length;i++){
							//alert(content[i].title);
							html += "<b>"+content[i].title+"</b>：";
							//html += "<p>"+content[i].type+"</p>";
							//html += "<p>"+content[i].content+"</p>";
							if (content[i].type == 2){
								if(content[i].content.indexOf("-=-") != -1){
									//alert(";");
									var opt = content[i].content.split("-=-");
									html += "<span>";
									for(var j=0;j<opt.length;j++){
										html += opt[j];
										if(j<opt.length-1){
											html += "、";
										}
									}
									
									html += "</span>";
									
								}else{
									html += "<span>"+content[i].content+"</span>";
								}
								//时间
							}else if(content[i].type == 4){
								var val = parseInt(content[i].content);
								//alert(content[i].content);
								html += "<span>"+formatTime(val)+"</span>";
							}else if(content[i].type == 5){
								var val = parseInt(content[i].content);
								html += "<span>"+formatDate(val)+"</span>";
							}else{
								html += "<span>"+content[i].content+"</span>";
							}
							html += "<hr>";
						}
						html += "<b>描述：</b><div style='background-color: #FFFFFF'>"+result.log.memo+"</div>";
						
						$("#diaryContent").html(html);
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
					<td><span id="diaryAuthorName"></span></td>
					<td class="addTdWidth"></td>
					<td>标题：</td>
					<td><span id="diaryAuthorTitle"></span></td>
				</tr>
			</table>
			
			
			</div>
			
		</div>
		<div id="diaryContent" style="width:100%;">
			
			
		</div>

	</body>

</html>