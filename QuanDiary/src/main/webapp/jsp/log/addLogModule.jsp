<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<script type="text/javascript">
			$(function(){
				$(document).ready(function() {
				    $('input[type=radio][name=selectTypeRadio]').change(function() {
						alert(this.value);
				    });
				});

			});
			function sumbitField(){
				if (confirm("您确认提交吗？")){
					var logTitle = $("#logTitle").val();
					if (logTitle == ""){
						alert("日志标题不能为空！");
						return;
					}
					var data = [];
					var i = 0;
					var isBreak = false;
					$('#fieldUl').find('li').each(function(){
						
						var nullData = $(this).data("data1");
						//alert(nullData);
						var title = $(this).children("input").val();
						if (title == "" || title == null){
							
							isBreak = true;
							alert("字段名称不能为空！");
							return;
						}
						if (nullData == "" || nullData == null || typeof(nullData) == "undefined"){
							
							data[i] = {"title":title,"type":0, "data1":"","orderNo":i};
							//console.log("null:"+data[i]);
						}else{
							var dataVal = $(this).data("data1");
							data[i] = {"title":title, "type":dataVal.type,"data1":dataVal.data1,"orderNo":i};
							//data[i] = $(this).data("data1");
							//console.log("notnull:"+data[i]);
						}
						i++;
						
					});
					if (isBreak) return;
					var content = JSON.stringify(data);
					$.ajax({
						url:path+"/QuanDiary/log/sumbitLogModule.do",
						type:"post",
						data:{"content":content, "logTitle":logTitle},
						dataType:"json",
						success:function(result){
							alert("添加成功！");
						}
					});
				}
			}

			function addField(){
				var val = "<br><li>名称：<input type='text' placeholder='请输入字段名称'>&nbsp;&nbsp;类型：<b class='handle' onclick='selectType1(this);'><u>文本>></u></b></li>";
				$("#fieldUl").append(val);
			}
			function selectTypeRadioSingleAddOptionButton(){
				var html = "<input type='text' placeholder='请填写选项名称'>";
				$("#selectTypeRadioSingleAddOption").append(html);
			}
			function selectTypeRadioMultipleAddOptionButton(){
				var html = "<input type='text' placeholder='请填写选项名称'>";
				$("#selectTypeRadioMultipleAddOption").append(html);
			}
			function selectType1(on, data){

				$('#fieldUl').find('li').each(function(){
					$(this).removeClass("divChecked");
				});
				$(on).parent().addClass("divChecked");
				var html = 	"";
				html += "<input class='handle' type='radio' name='selectTypeRadio' value='0' checked='checked'>【文本】填写内容<br>";
				html += "<input class='handle' type='radio' name='selectTypeRadio' value='1'>【单选】只能选择一项<br>";
				html += "<div id='selectTypeRadioSingleDiv'>";
				html += "<div id='selectTypeRadioSingleAddOption'><button class='handle' onclick='selectTypeRadioSingleAddOptionButton();'>+</button><input type='text' placeholder='请填写选项名称'></div>";
				html += "</div>";
				html += "<input class='handle' type='radio' name='selectTypeRadio' value='2'>【多选】可以选择多项<br>";
				html += "<div id='selectTypeRadioMultipleAddOption'><button class='handle' onclick='selectTypeRadioMultipleAddOptionButton();'>+</button><input type='text' placeholder='请填写选项名称'></div>";
				html += "<input class='handle' type='radio' name='selectTypeRadio' value='3'>【数字】只能填写数字<br>";
				html += "<input class='handle' type='radio' name='selectTypeRadio' value='4'>【时间】如：2019-01-18 13:14<br>";
				html += "<input class='handle' type='radio' name='selectTypeRadio' value='5'>【日期】如：2019-01-18<br>";
				html += "<input class='handle' type='button' onclick='goSubmit();' value='确定'/><br>";
				$("#selectTypeShowDiv").html(html);
				$("#selectTypeShowDiv").show();
			}
			function goSubmit(){
				var data;
				var typeVal = $("input[name='selectTypeRadio']:checked").val();
				if (typeVal == 1){
					var val = "";
					var single = $("#selectTypeRadioSingleAddOption input");
					if(single.length < 1){
						alert("单选不得少于一项！");
						return;
					}else{
						
						var isReturn = false;
						$("#selectTypeRadioSingleAddOption input").each(function(){
							if ($(this).val() == "" || $(this).val() == null){
								alert("字段名称不能为空！");
								isReturn = true;
							}
							val += $(this).val();
							val += "-=-";
						});
						val = val.substring(0, val.length-3);
						if (isReturn){
							return;
						}
					}
					data = {"type":1, "data1": val};
				}else if(typeVal == 2){
					var val = "";
					var single = $("#selectTypeRadioMultipleAddOption input");
					if(single.length < 2){
						alert("多选不得少于两项！");
						return;
					}else{
						
						var isReturn = false;
						$("#selectTypeRadioMultipleAddOption input").each(function(){
							if ($(this).val() == "" || $(this).val() == null){
								alert("字段名称不能为空！");
								isReturn = true;
							}
							val += $(this).val();
							val += "-=-";
						});
						val = val.substring(0, val.length-3);
						if (isReturn){
							return;
						}
					}
					data = {"type":2, "data1": val};
				}else{
					data = {"type":typeVal, "data1": ""};
				}
				$('#fieldUl').find('li').each(function(){
					if($(this).hasClass("divChecked")){
						$(this).data("data1", data);
					}
				});
				switch(typeVal){
					case "0": change("文本>>");
			  			break;
					case "1": change("单选>>");
			  			break;
					case "2": change("多选>>");
			  			break;
					case "3": change("数字>>");
			  			break;
					case "4": change("时间>>");
			  			break;
					case "5": change("日期>>");
				  		break;
				}
				$("#selectTypeShowDiv").hide();
			}
			function change(val){
				$('#fieldUl').find('li').each(function(){
					if($(this).hasClass("divChecked")){
						var b = $(this).children("b");
						b.html(val);
					}
				});
			}

		</script>
	</head>
	<body>
		<div>
			<input type="button"  class='handle' onclick="sumbitField();" value="提交">
			<br><br>
			日志标题：<input id="logTitle" type="text" placeholder="请输入日志标题"/>
			<hr>
			<br>
			<b>请完善以下字段：</b><br>
			<ul id="fieldUl" style="list-style-type:none">
				<li>
					名称：<input type="text" placeholder="请输入字段名称">&nbsp;&nbsp;类型：<b class='handle' onclick="selectType1(this);"><u>文本>></u></b>
				</li>
			</ul>
			<u class='handle' onclick="addField();">添加字段</u>
			
		</div>
		<br><hr>
		<div id="selectTypeShowDiv" style="display:none;">
			<input type="radio" name="selectTypeRadio" value="0" checked="checked">【文本】填写内容<br>
			<input type="radio" name="selectTypeRadio" value="1">【单选】只能选择一项<br>
				<div id="selectTypeRadioSingleDiv">
					<div id="selectTypeRadioSingleAddOption"><button onclick="selectTypeRadioSingleAddOptionButton();">+</button><input type="text" placeholder='请填写选项名称'></div>
				</div>
			<input type="radio" name="selectTypeRadio" value="2">【多选】可以选择多项<br>
				<div id="selectTypeRadioMultipleAddOption"><button onclick="selectTypeRadioMultipleAddOptionButton();">+</button><input type="text" placeholder='请填写选项名称'></div>
			<input type="radio" name="selectTypeRadio" value="3">【数字】只能填写数字<br>
			<input type="radio" name="selectTypeRadio" value="4">【时间】如：2019-01-18 13:14<br>
			<input type="radio" name="selectTypeRadio" value="5">【日期】如：2019-01-18<br>
			<input type="button" onclick="goSubmit();" value="确定"/><br>
		</div>
		
	</body>

</html>