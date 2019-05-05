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
	    	        url : path+'/QuanDiary/company/loadApprovalData.do',
	    	        datatype : "json",
	    	        //colNames : [ 'id', '名字', '所在部门', 'status', '操作'],
	    	        colNames : [ 'id', '名字', '所在部门', '申请类型', '申请理由', '申请结果', '创建时间', '操作'],
	    	        colModel : [ 
	    	                     {name : 'id',index : 'id',width : 100,hidden:true}, 
	    	                     {name : 'displayName',index : 'displayName',width : 200, align:"center"}, 
	    	                     {name : 'deptName',index : 'deptName',width : 100, align:"center"},
	    	                     {name : 'type',index : 'type',width : 100, align:"center", formatter: changeAppName},
	    	                     {name : 'reason',index : 'reason',width : 100, align:"center"},
	    	                     {name : 'aStatus',index : 'aStatus',width : 100, align:"center", formatter: changeAppStatus},
	    	                     {name : 'appTime',index : 'appTime',width : 100, align:"center", formatter: changeAppTime}, 

	    	                     {label: '操作', align: 'center', name: 'state', index: 'state', width: 100, edittype:"button", 
	    	                    	 formatter: appListOpt }
	    	                   ],
	    	        rowNum : 20,
	    	        rownumbers: true,
	    	        autowidth:true,
	    	        height:'520px',
	    	        rowList : [ 10, 20, 50, 100],
	    	        pager : '#pager2',
	    	        sortname : 'id',
	    	        mtype : "post",
	    	        viewrecords : true,
	    	        sortorder : "desc",
	    	        caption : "员工申请列表"
				});
		/*创建jqGrid的操作按钮容器*/
		/*可以控制界面上增删改查的按钮是否显示*/
		jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
	}
	$(function(){

		pageInit();

	});
	function changeAppStatus(val){
		//console.log(val);
		if(val == 16){
			return "<span style='color:#EE82EE;'>未被处理</span>";
		}else if(val == 18){
			return "<span style='color:red;'>拒绝</span>";
		}else if(val == 19){
			return "<span style='color:green;'>同意</span>";
		}
	}
	function appListOpt(value, grid, rows, state) { 
		var html = "<button class='handle' onclick='show("+rows.id+")'>查看</button>&nbsp;";
		html += "<button class='handle' onclick='updateApprovalStatus("+rows.id+",19)'>同意</button>&nbsp;";
		html += "<button class='handle' onclick='updateApprovalStatus("+rows.id+",18)'>拒绝</button>&nbsp;";
		return html;
	}
	function updateApprovalStatus(id, status){
		
		$.ajax({
				
			url:path+"/QuanDiary/company/deadApprovalById.do",
			type:"post",
			data:{"id":id, "status":status},
			dataType:"json",
			success:function(result){
				alert(result.msg);
				reloadGrid();
			}
		});

	}
	function reloadGrid(){
        $("#list2").jqGrid('setGridParam',{
            datatype:'json',
            postData:{}, //发送数据
            page:1
        }).trigger("reloadGrid");
	}
	function show(id){
		
		$.ajax({
				
			url:path+"/QuanDiary/company/loadApprovalById.do",
			type:"post",
			data:{"id":id},
			//dataType:"json",
			success:function(result){
				openWindow("查看员工申请", result);

			}
		});
	}
	function changeAppName(val){
		switch (val) {
		case 11:
			return "<span style='color:red;'>离职</span>";
			break;
		case 13:
			return "<span style='color:#EE82EE;'>请假</span>";
			break;
		case 14:
			return "<span style='color:#green;'>出差</span>";
			break;
		default:
			break;
		}
	}
	function changeAppTime(val){
		return formatTime(val*1000);
	}
	function cradListSearchTime(){
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
		<!-- <div style="height:20px;background-color: #D1EEEE;" >
			请选择查询日期：<input type="date" id="cradListSearchTime" onchange="cradListSearchTime();">
		</div> -->
		<table id="list2"></table> 
		<div id="pager2"></div>

</body>
</html>