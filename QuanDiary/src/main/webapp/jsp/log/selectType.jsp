<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">
		#selectTypeRadioSingleAddOption,#selectTypeRadioMultipleAddOption{
			display:none;
		}
	</style>
    <script type="text/javascript">
		$(function(){
			$(document).ready(function() {
			    $('input[type=radio][name=selectTypeRadio]').change(function() {
			        if (this.value == '1') {
			        	$("#selectTypeRadioMultipleAddOption").hide();
			            $("#selectTypeRadioSingleAddOption").show();
			        }else if (this.value == '2') {
			        	$("#selectTypeRadioMultipleAddOption").show();
			            $("#selectTypeRadioSingleAddOption").hide();
			        }else{
			        	$("#selectTypeRadioMultipleAddOption").hide();
			            $("#selectTypeRadioSingleAddOption").hide();
			        }
			    });
			});

		});
		function selectTypeRadioSingleAddOptionButton(){
			var inputHtml = "<input type='text' placeholder='请填写选项名称'>";
			$("#selectTypeRadioSingleAddOption").append(inputHtml);
		}
		function selectTypeRadioMultipleAddOptionButton(){
			var inputHtml = "<input type='text' placeholder='请填写选项名称'>";
			$("#selectTypeRadioMultipleAddOption").append(inputHtml);
		}
		function goSubmit(){
			var selectVal = $("input[name='selectTypeRadio']:checked").val();
			if (selectVal == 1){
				var single = $("#selectTypeRadioSingleAddOption input");
				if(single.length < 1){
					alert("单选不得少于一项！");
				}else{
					var val = "";
					var isReturn = false;
					$("#selectTypeRadioSingleAddOption input").each(function(){
						if ($(this).val() == "" || $(this).val() == null){
							alert("字段名称不能为空！");
							isReturn = true;
						}
						val += $(this).val();
						val += "-=-";
					});
					if (isReturn){
						return;
					}
					//alert(val.substring(0, val.length-3));
					//alert(single[0]);
					closeWindow();
				}
			}else if(selectVal == 2){
				var single = $("#selectTypeRadioMultipleAddOption input");
				if(single.length < 2){
					alert("单选不得少于两项！");
				}else{
					var val = "";
					var isReturn = false;
					$("#selectTypeRadioMultipleAddOption input").each(function(){
						if ($(this).val() == "" || $(this).val() == null){
							alert("字段名称不能为空！");
							isReturn = true;
						}
						val += $(this).val();
						val += "-=-";
					});
					if (isReturn){
						return;
					}
					//alert(val.substring(0, val.length-3));
					closeWindow();
				}
			}else {
				
			}
		}
		function data(){
			alert(1);
		}
    </script>
</head>
<body>
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
</body>
</html>