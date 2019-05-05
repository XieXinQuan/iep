<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<script type="text/javascript">
		var id = ${id };
			var diaryTag;
			var diaryModuleId;
			$(function(){
				//alert(id);
				$.ajax({
					url:path+"/QuanDiary/log/viewDiaryModuleById.do",
					type:"post",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						//var log = result.log;
						//console.log(result.log.id+":"+result.log.title);
						$("#logModuleTitle").text(result.log.title);
						var tags = result.tags;
						var html = "";
						for(var i=0;i<tags.length;i++){
							//console.log(tags[i].title);
							html += "<b>标题："+tags[i].title+"</b>&nbsp;&nbsp;&nbsp;&nbsp;";
							html += "类型："+conversion1(tags[i].type, tags[i].content)+"。<hr>";
						}
						$("#logModuleTagInfor").html(html);
					}
				});
			});
			function conversion1(val, content){
				switch (val) {
				case 0:
					return "【文本】填写内容";

				case 1:

					return "【单选】只能选择一项。选项："+opt(content);

				case 2:
					
					return "【多选】可以选择多项。选项："+opt(content);

				case 3:
					return "【数字】只能填写数字";

				case 4:
					return "【时间】如：2019-01-18 13:14";

				case 5:
					return "【日期】如：2019-01-18";

				default:
					break;
				}
			}
			function opt(content){
				var html = "";
				if(content.indexOf("-=-") != -1){
					//alert(";");
					var opt = content.split("-=-");
					html += "<span>";
					for(var j=0;j<opt.length;j++){
						html += opt[j];
						if(j<opt.length-1){
							html += "、";
						}
					}
					
					html += "</span>";
					
				}else{
					html += "<span>"+content+"</span>";
				}
				return html;
			}
		</script>
	</head>
	<body>

			<div id="logModuleInfor" style="background-color: #DDA0DD">
			
				<table style="margin-left: 480px;">
					<tr>
						<td>模板标题：</td>
						<td><span id="logModuleTitle"></span></td>
					</tr>
				</table>
			
			
			</div>
		
		<div id="logModuleTagInfor">
			
		</div>
	</body>

</html>