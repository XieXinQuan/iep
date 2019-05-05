<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	.datainp{ 
		width:200px; height:30px; border:1px #ccc solid;
	}
</style>

<script type="text/javascript">
	var type = 0;
	function askForLeave(val){
		if(val == 1){
			$("#cardContent").hide();
			$("#sumbitAskForLeave").show();
			$("#departureShowDiv").hide();
			$("#askForLeaveShowDiv").show();
			$("#askForLeaveB").text("申请请假审批");
		}else if(val == 2){
			$("#cardContent").hide();
			$("#sumbitAskForLeave").show();
			$("#departureShowDiv").hide();
			$("#askForLeaveShowDiv").show();
			$("#askForLeaveB").text("申请出差审批");
		}else if(val == 3){
			$("#cardContent").hide();
			$("#sumbitAskForLeave").show();
			$("#departureShowDiv").show();
			$("#askForLeaveShowDiv").hide();
			$("#askForLeaveB").text("申请离职审批");
		}else if(val == 0){
			$("#departureShowDiv").hide();
			$("#askForLeaveShowDiv").hide();
			$("#cardContent").show();
			var date = new Date();
			var hour = date.getHours();
			var minutes = date.getMinutes();
			$("#cardTime").text("现在是："+hour+":"+minutes+"  ");
			$("#sumbitAskForLeave").hide();
			$("#askForLeaveB").text("每日考勤打卡签到");
		}
		type = val;
		$("#askForLeaveContent").val("");
		$("#askForLeaveDiv").show();
	}
	function card(){
		$.ajax({

			url:path+"/QuanDiary/company/saveAskForLeave.do",
			type:"post",
			data:{"type":type},
			dataType:"json",
			success:function(result){
				alert(result.msg);
			}
		});
	}
	
	function sumbitAskForLeave(){

		
		if(type == 1 || type == 2){
			if(checkVal($("#askForLeaveStartTime").val(), "开始时间")) return;
			if(checkVal($("#askForLeaveEndTime").val(), "结束时间")) return;
			if(checkVal($("#askForLeaveContent").val(), "申请理由")) return;
			var startTime = new Date($("#askForLeaveStartTime").val()).getTime();
			var endTime = new Date($("#askForLeaveEndTime").val()).getTime();
			var content = $("#askForLeaveContent").val();
			$.ajax({

				url:path+"/QuanDiary/company/saveAskForLeave.do",
				type:"post",
				data:{"type":type,"startTime":startTime, "endTime":endTime, "content":content},
				dataType:"json",
				success:function(result){
					alert(result.msg);
				}
			});
		}else if(type == 3){
			if(checkVal($("#departureEndTime").val(), "离司日期")) return;
			if(checkVal($("#departureContent").val(), "申请理由")) return;
			
			var startTime = new Date($("#departureEndTime").val()+" 00:00").getTime();
			
			var content = $("#departureContent").val();
			$.ajax({

				url:path+"/QuanDiary/company/saveAskForLeave.do",
				type:"post",
				data:{"type":type,"startTime":startTime, "content":content},
				dataType:"json",
				success:function(result){
					alert(result.msg);
				}
			});
		}
	}
	function checkVal(val,text){
		var isReturn = false;
		if(val == null || val == ""){
			alert(text+"不能为空！");
			isReturn = true;

		}
		return isReturn;
	}
	$(function(){
		$("#approvalMenu u").live("click", function() {
			$('#approvalMenu').find('u').each(function(){
				$(this).removeClass("secondMenuSelect");
			});
			
			$(this).addClass("secondMenuSelect");
		});
	});
	jeDate({
		dateCell:"#askForLeaveEndTime",//isinitVal:true,
		format:"YYYY-MM-DD hh:mm",
		isinitVal:true,
		isTime:true //isClear:false,
		//minDate:"2015-10-19 00:00:00",
		//maxDate:"2016-11-8 00:00:00"
	});
	jeDate({
		dateCell:"#askForLeaveStartTime",//isinitVal:true,
		format:"YYYY-MM-DD hh:mm",
		isinitVal:true,
		isTime:true //isClear:false,
		//minDate:"2015-10-19 00:00:00",
		//maxDate:"2016-11-8 00:00:00"
	});
    jeDate({
		dateCell:"#departureEndTime",
		format:"YYYY-MM-DD",
		isinitVal:true,
		isTime:false //isClear:false,
		//minDate:"2014-09-19 00:00:00",
		//okfun:function(val){alert(val)}
	});
    
</script>
</head>
<body>
	<div id="approvalMenu">
		&nbsp;
		<u class='handle' onclick="askForLeave(0);">考勤</u>
		<u class='handle' onclick="askForLeave(1);">请假</u>
		<u class='handle' onclick="askForLeave(2);">出差</u>
		<u class='handle' onclick="askForLeave(3);">离职</u>

	</div>
	<hr>
	<br>
	
	<div id="askForLeaveDiv" style="display: none;">
		<b id="askForLeaveB">申请请假审批</b><hr><br>
		<div id="cardContent" style="display: none;">
			<span id="cardTime"></span><u class='handle' onclick="card();">每日签到</u>
		</div>
		<input id="sumbitAskForLeave" class='handle' type="button" value="提交" onclick="sumbitAskForLeave();"><br><br>
		<div style="display: none;" id="askForLeaveShowDiv">
			
			

			<b>请选择开始时间：</b><input class="datainp" id="askForLeaveStartTime" type="text" value="" readonly><br><br>
			<b>请选择结束时间：</b><input class="datainp" id="askForLeaveEndTime" type="text" value="" readonly><br>
			<b>请填写申请理由：</b><textarea id="askForLeaveContent"></textarea>
		</div>
		<div style="display: none;" id="departureShowDiv">
			<b id="departureTimeB">请选择最后离司日期：</b><input class="datainp" id="departureEndTime" type="text" readonly><br>
			<b>请填写申请理由：</b><textarea id="departureContent"></textarea>
		</div>
		
	</div>
</body>
</html>