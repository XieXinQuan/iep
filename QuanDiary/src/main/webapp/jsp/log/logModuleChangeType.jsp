<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">
		$(function(){
			
		});
    </script>
</head>
<body>
			<div id="selectTypeHideDiv" style="display:none;">
			<div id="selectType">
				<input type="radio" name="selectTypeRadio" value="0" checked="checked">【文本】填写内容<br>
				<input type="radio" name="selectTypeRadio" value="1">【单选】只能选择一项<br>
					<div id="selectTypeRadioSingleDiv">
						<div id="selectTypeRadioSingleAddOption"><input type="text" placeholder='请填写选项名称'><input type="text" placeholder='请填写选项名称'></div>
						<button onclick="selectTypeRadioSingleAddOptionButton();">+</button>
					</div>
				<input type="radio" name="selectTypeRadio" value="2">【多选】可以选择多项<br>
					<div id="selectTypeRadioMultipleAddOption"><input type="text" placeholder='请填写选项名称'><input type="text" placeholder='请填写选项名称'><button onclick="selectTypeRadioMultipleAddOptionButton();">+</button></div>
				<input type="radio" name="selectTypeRadio" value="3">【数字】只能填写数字<br>
				<input type="radio" name="selectTypeRadio" value="4">【时间】如：2019-01-18 13:14<br>
				<input type="radio" name="selectTypeRadio" value="5">【日期】如：2019-01-18<br>
				<input type="button" onclick="submitField();" value="确定"/><br>
			</div>
		</div>
</body>
</html>