<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	

		<script type="text/javascript">
		

			var diaryTag;
			var diaryModuleId;
			$(function(){
				$.ajax({
					url:path+"/QuanDiary/log/loadModuleId.do",
					type:"post",
					data:{},
					dataType:"json",
					success:function(result){
						var html = "";
						if (result.status == 88){
							alert("请重新登录！");
							setTimeout("window.location='../login/login1.html';", 500);
						}else if(result.status == 2){
							html += "<span style='color:red;'>暂无模板，请通知管理员添加！</span>";
							$("#loadModuleId").html(html);
						}else if(result.status == 1){
							html += "<b>请选择模板：</b>";
							for(var i=0;i<result.content.length;i++){
								//html += "<input  class='handle' type='button' onclick='loadModuleContent("+result.content[i].id+");' value='"+result.content[i].title+"'>";
								html += "<u class='handle' onclick='loadModuleContent("+result.content[i].id+");'>"+result.content[i].title+"</u>";
								html += "&nbsp;";
							}
							$("#loadModuleId").html(html);
						}
					}
				});
				$("#loadModuleId u").live("click", function() {
					$('#loadModuleId').find('u').each(function(){
						$(this).removeClass("secondMenuSelect");
					});
					
					$(this).addClass("secondMenuSelect");
				});
			});

			function loadModuleContent(id){
				

				
				diaryModuleId = id;
				$.ajax({
					url:path+"/QuanDiary/log/loadModuleById.do?moduleId="+id,
					type:"post",
					data:{},
					dataType:"json",
					success:function(result){
						if(result.status == 1){
							//$("#editor").show();
							
							diaryTag = result.content;
							//console.log(diaryTag);
							var html = "<input class='handle' type='button' onclick='sumbitDiary();' value='提交'><br><br>";
							html += "<b>日志标题：</b> <input type='text' id='diaryTitle'/><br><hr>";
							for(var i=0;i<result.content.length;i++){
								var content = result.content[i];
								if (content.type == 0){
									html += "<b>"+content.title+"：</b><textarea id='field"+i+"'></textarea><br>";
									
									//html += "<b>"+content.title+"：</b><input type='text' id='field"+i+"'><br>";
								}else if(content.type == 1){
									var option = content.content.split("-=-");
									html += "<b>"+content.title+"：</b>";
									for (var j=0;j<option.length;j++){
										html += "<input type='radio' name='field"+i+"' value='"+j+"'>"+option[j]+"&nbsp;";
									}
									html += "<br>";
									
								}else if(content.type == 2){
									var option = content.content.split("-=-");
									html += "<b>"+content.title+"：</b>";
									for (var j=0;j<option.length;j++){
										html += "<input type='checkbox' id='field"+i+"' name='field"+i+"' value='"+j+"'>"+option[j]+"&nbsp;";
									}
									html += "<br>";
									
								}else if(content.type == 3){
									var num = "value=value.replace(/[^\\d]/g,'')";
									html += "<b>"+content.title+"：</b><input type='text' id='field"+i+"' oninput = \""+num+"\"><br>";
								}else if(content.type == 4){
									html += "<b>"+content.title+"：</b>";
									html += "<input type='datetime-local' id='field"+i+"'><br>";
								}else if(content.type == 5){
									html += "<b>"+content.title+"：</b>";
									html += "<input type='date' id='field"+i+"'><br>";
								}else if(content.type == 6){
									
								}
								
							}
							html += "<br>";
							$("#loadModuleContent").html(html);
						}else {
							$("#loadModuleContent").html("系统错误！");
						}
						
					}
				});
			}
			function sumbitDiary(){
				var stem = CKEDITOR.instances.editor.getData();
				
				
				if (diaryTag != null){
					var diaryTitle = $("#diaryTitle").val();
					if (diaryTitle == ""){
						alert("日志标题不能为空！");
						return ;
					}
					var sumbitDiary = [];
					for (var i = 0;i<diaryTag.length;i++){
						var content = diaryTag[i];
						if (content.type == 0 || 
								content.type == 3){
							var val = $("#field"+i).val();
							if(val == null || val == ""){
								alert(content.title+" 不能为空！");
								return;
							}
							sumbitDiary[i] = {"content":val,"tagTitle":content.title,"orderNo":i};
							
						}else if(content.type == 4 ||
								content.type == 5){
							var val = $("#field"+i).val();
							if(val == null || val == ""){
								alert(content.title+" 不能为空！");
								return;
							}
							var timeStamp = new Date(val).getTime();
							sumbitDiary[i] = {"content":timeStamp,"tagTitle":content.title,"orderNo":i};
						}else if (content.type == 1){
							
							var val = $("input[name='field"+i+"']:checked").val();
							
							if(typeof(val) == "undefined"){
								alert(content.title+" 不能为空！");
								return;
							}
							sumbitDiary[i] = {"content":val,"tagTitle":content.title,"orderNo":i};
						}else if (content.type == 2){

							var val ="";//定义一个数组    
				            $('input[name="field'+i+'"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
				            	val += $(this).val()+",";//将选中的值添加到数组chk_value中    
				            });
							if(val.length == ""){
								alert(content.title+" 不能为空！");
								return;
							}
							sumbitDiary[i] = {"content":val.substring(0,val.length-1),"tagTitle":content.title,"orderNo":i};
						}
					}
					$.ajax({
						url:path+"/QuanDiary/log/saveDiary.do",
						type:"post",
						data:{"title":diaryTitle,"memo":stem, "diaryModuleId":diaryModuleId,"content":JSON.stringify(sumbitDiary)},
						dataType:"json",
						success:function(result){
							if(result.status == 88){
								alert("登录信息已过期，请重新登录！");
								setTimeout("window.location='../login/login1.html';", 500);
							}else if (result.status == 1){
								alert("保存成功！");
							}
						}
					});
				}
				
			}
		</script>
	</head>


<body>
		<div id="loadModuleId">
			
		</div>
		<hr><br>
		
		<br>
		<div id="loadModuleContent">
			
		</div>
	<div>
		<b>可编写文档：</b><br>
		<div id="editor">
       		
    	</div>
	</div>

<script>

    CKEDITOR.replace( 'editor' );


        $("#one").click(function () {
        
        var stem = CKEDITOR.instances.editor.getData();
        	alert(""+stem);
        });



</script>
	</body>

</html>