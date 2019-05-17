<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<script type="text/javascript">
			var id = ${id };
			var userType = ${userType };
			var title = "${title }";
			var author = "${userName }";
			
			$(function(){

				$("#diaryAuthorName").text(author);
				$("#diaryAuthorTitle").text(title);
				loadDiaryComment();
			});
			function loadDiaryComment(){
				$.ajax({
					url:path+"/QuanDiary/log/viewDiaryCommentById.do",
					type:"post",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						var likes = result.likes;
						var likeHtml = "";
						for(var i=0;i<likes.length;i++){
							likeHtml += likes[i].displayName + " 点赞了此条日志。";
							
							likeHtml += "<span style='float:right;'>"+formatTime(likes[i].createTime*1000)+"</span><br>";
							//console.log(likes[i].createTime);
							//likeHtml += likes[i].displayName + "在" +formatTime(likes[i].createTime*1000)+"点赞了此条日志。<br>";
						}
						$("#loadLogLike").append(likeHtml);
						var reads = result.reads;
						var readsHtml = "";
						for(var i=0;i<reads.length;i++){
							readsHtml += reads[i].displayName + " 查看了此条日志。";
							readsHtml += "<span style='float:right;'>"+formatTime(reads[i].createTime*1000)+"</span><br>";
							//console.log(likes[i].createTime);
							//readsHtml += reads[i].displayName + "在" +formatTime(reads[i].createTime*1000)+"查看了此条日志。<br>";
						}
						$("#loadLogRead").append(readsHtml);
						var comments = result.comments;
						
						var commentsHtml = "";
						for(var i=0;i<comments.length;i++){
							//console.log(likes[i].createTime);
							commentsHtml += "<div id='comment"+comments[i].id+"'>";
							commentsHtml += comments[i].displayName + " ： " + comments[i].content;
							if(userType == 3 || userType == 4){
								commentsHtml += "<img src='../images/icon/exit.png' style='height:16px;width:16px;' class='handle' onclick='delComment("+comments[i].id+");'/>";
							}
							commentsHtml += "<span style='float:right;'>"+formatTime(comments[i].createTime*1000)+"</span></div>";
							//alert(comments[i].displayName);
							//commentsHtml += comments[i].displayName + "在" +formatTime(comments[i].createTime*1000)+"发表了评论："+comments[i].content+"。<br>";
						}
						$("#loadLogComment").append(commentsHtml);
					}
				});
			}
			function delComment(id){
				if (confirm("您确定要删除此条评论吗？")){
					$.ajax({
						url:path+"/QuanDiary/log/delComment.do",
						type:"post",
						data:{"id":id},
						dataType:"json",
						success:function(result){
							if(result.status == 1){
								$("#comment"+id).html("");
							}
							alert(result.msg);
						}
					});
				}

			}
			function sumbitCommentContent(){
				
				var content = $("#commentContent").val();
				if(content == null || content == "") return;
				$.ajax({
					url:path+"/QuanDiary/log/sumbitDiaryCommentContent.do",
					type:"post",
					data:{"id":id, "content":content},
					dataType:"json",
					success:function(result){
						if(result.status == 1){
							$("#loadLogComment").append("我刚刚发表了评论："+content+"。<br>");
						}
					}
				});
			}
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
			<br>
			<div id="loadLogLike">
				<span style="color:#CD3700;">点赞记录：</span><hr>
			</div>
			<hr>
			<br>
			<div id="loadLogRead">
				<span style="color:#CD3700;">查看记录：</span><hr>
			</div>
						<hr>
			<br>
			<div id="loadLogComment">
				<span>发表评论：</span><textarea id="commentContent" rows="3" cols="100" placeholder="请输入评论内容"></textarea>
				<input type="button" value="评论" onclick="sumbitCommentContent();"> <br>
				<span style="color:#CD3700;">评论记录：</span><hr>
			</div>
		</div>


	</body>

</html>