<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	
</style>
<script type="text/javascript">






	function pageInit(){
		//创建jqGrid组件
		jQuery("#list2").jqGrid(
				{
	    	        url : path+'/QuanDiary/company/cardListData.do',
	    	        datatype : "json",
	    	        //colNames : [ 'id', '名字', '所在部门', 'status', '操作'],
	    	        colNames : [  '名字', '所在部门', '是否签到', '签到时间'],
	    	        colModel : [ 
	    	                     //{name : 'id',index : 'id',width : 100,hidden:true}, 
	    	                     {name : 'displayName',index : 'displayName',width : 200, align:"center"}, 
	    	                     {name : 'deptName',index : 'deptName',width : 100, align:"center"},
	    	                     {name : 'cardMsg',index : 'cardMsg',width : 200, align:"center"}, 
	    	                     {name : 'time',index : 'time',width : 200, align:"center"}
	    	                 //    {name : 'status',index : 'status',width : 100, align:"center", formatter: changeStatus},
	    	                 //    {label: '操作', align: 'center', name: 'state', index: 'state', width: 100, edittype:"button", 
	    	                 //   	 formatter: function (value, grid, rows, state) { return "<button onclick='show("+rows.id+")'>查看</button>&nbsp;<button onclick='del("+rows.id+")'>删除</button>" } }
	    	                   ],
	    	        rowNum : 20,
	    	        rownumbers: true,
	    	        autowidth:true,
	    	        height:'500px',
	    	        rowList : [ 10, 20, 50, 100],
	    	        pager : '#pager2',
	    	        sortname : 'id',
	    	        mtype : "post",
	    	        viewrecords : true,
	    	        sortorder : "desc",
	    	        caption : "签到记录"
				});
		/*创建jqGrid的操作按钮容器*/
		/*可以控制界面上增删改查的按钮是否显示*/
		jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
	}
	$(function(){

		pageInit();

	});
	function cradListSearchTime(){
		/*var startTime = new Date($("#cradListSearchTime").val());
		
		var year = startTime.getFullYear();
		var month = startTime.getMonth()+1;
		var day = startTime.getDate();
		var monthStr = "";
		var dayStr = "";
		if(month < 10){
			monthStr = "0"+month;
		}else{
			monthStr = ""+month;
		}
		if(day < 10){
			dayStr = "0"+day;
		}else{
			dayStr = ""+day;
		}
		var time = ""+year+"-"+monthStr+"-"+dayStr;*/
		var time = $("#cradListSearchTime").val();
		//console.log(time);
        $("#list2").jqGrid('setGridParam',{
            datatype:'json',
            postData:{"yearMonthDay":time}, //发送数据
            page:1
        }).trigger("reloadGrid");
	}
</script>
</head>
<body>
		<div style="height:30px;background-color: #D1EEEE;" >
			请选择查询日期：<input type="date" id="cradListSearchTime" onchange="cradListSearchTime();">
		</div>
		<table id="list2"></table> 
		<div id="pager2"></div>

</body>
</html>