<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="QuanDiary.Util.enumUtil.CommonStatus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

	
		
		<!-- 本页面初始化用到的js包，创建jqGrid的代码就在里面 
		<script type="text/javascript" src="../../js/index.js"></script>-->
		<script type="text/javascript">
		$(function(){
			//页面加载完成之后执行
			//alert("${userId }");
			pageInit();
		});
		function pageInit(){
			//创建jqGrid组件
			jQuery("#list2").jqGrid(
					{
		    	        url : path+'/QuanDiary/log/logWritingSituationData.do',
		    	        datatype : "json",
		    	        colNames : [ '名字', '所在部门', '是否完成日志', '是否签到', '是否请假'],
		    	        colModel : [ 
		    	                     //{name : 'id',index : 'id',width : 100,hidden:true}, 
		    	                     {name : 'displayName',index : 'displayName',width : 200, align:"center"}, 
		    	                     {name : 'deptName',index : 'deptName',width : 200, align:"center"}, 
		    	                     {name : 'logNum',index : 'logNum',width : 200, align:"center", formatter: changeLogNum}, 
		    	                     {name : 'cardNum',index : 'cardNum',width : 200, align:"center", formatter: changeCardNum}, 
		    	                     {name : 'appNum',index : 'appNum',width : 200, align:"center", formatter: changeAppNum}
		    	                     //{name : 'status',index : 'status',width : 100, align:"center", formatter: changeStatus},
		    	                    // {name : 'create_time',index : 'create_time',width : 100, align:"center", formatter: changeCreateTime},
		    	                     
		    	                   ],
		    	        rowNum : 20,
		    	        autowidth:true,
		    	        rownumbers: true,
		    	        height:'500px',
		    	        rowList : [ 10, 20, 50, 100],
		    	        pager : '#pager2',
		    	        sortname : 'id',
		    	        mtype : "post",
		    	        viewrecords : true,
		    	        sortorder : "desc",
		    	        caption : "员工日志写作情况"
					});
			/*创建jqGrid的操作按钮容器*/
			/*可以控制界面上增删改查的按钮是否显示*/
			jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
			var date=new Date;
			var year=date.getFullYear(); 
			var month=date.getMonth()+1;
			//console.log("年："+year+",月："+month);
			for(var i = 0;i < 3;i++){
				var year1 = year - i;
				$("#selectYearOption").append("<option value='"+year1+"'>"+year1+"</option>");
			}
			for(var i=month;i>=1;i--){
				$("#selectMonthOption").append("<option value='"+i+"'>"+i+"</option>");
			}
			$("#selectYearOption").change(function(){
				reloadLogList($("#selectYearOption").val(), $("#selectMonthOption").val());
				var date=new Date;
				var year2 = date.getFullYear(); 
				var month2 = date.getMonth()+1;
				if($("#selectYearOption").val() != year2){
					var html = "";
					for(var i=12;i>=1;i--){
						html += "<option value='"+i+"'>"+i+"</option>";
					}
					$("#selectMonthOption").html(html);
				}else{
					var html = "";
					for(var i=month2;i>=1;i--){
						html += "<option value='"+i+"'>"+i+"</option>";
					}
					$("#selectMonthOption").html(html);
				}
			});
			$("#selectMonthOption").change(function(){
				reloadLogList($("#selectYearOption").val(), $("#selectMonthOption").val());
			});
		}

		function changeLogNum(val){
			if(val != 0) {
				return "<span style='color:green;'>已完成</span>";
			}else{
				return "<span style='color:red;'>未完成</span>";
			}
			
		}
		function changeCardNum(val){
			if(val != 0) {
				return "<span style='color:green;'>已签到</span>";
			}else{
				return "<span style='color:red;'>未签到</span>";
			}
			
		}
		function changeAppNum(val){
			if(val != 0) {
				return "<span style='color:green;'>已请假</span>";
			}else{
				return "<span style='color:red;'>未请假</span>";
			}
			
		}


	    function reloadLogList(){
	    	var time = $("#logWriterSearchTime").val();
	    	
	        $("#list2").jqGrid('setGridParam',{
	            datatype:'json',
	            postData:{"time":time}, //发送数据
	            page:1
	        }).trigger("reloadGrid");
	    }
		</script>
	</head>
	<body>
		<div style="height:25px;background-color: #D1EEEE;" >
			请选择查询日期：<input id="logWriterSearchTime" type="date" onchange="reloadLogList();">
		</div>
		<table id="list2"></table> 
		<div id="pager2"></div>
		<br>

		
	</body>

</html>