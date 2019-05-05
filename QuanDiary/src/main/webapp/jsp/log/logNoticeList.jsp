<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="QuanDiary.Util.enumUtil.CommonStatus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>


		
		<!-- 本页面初始化用到的js包，创建jqGrid的代码就在里面 
		<script type="text/javascript" src="../../js/index.js"></script>-->
		<script type="text/javascript">
		var userType = ${userType };
		
		$(function(){
			//页面加载完成之后执行
			//alert("${userId }");
			pageInit();
		});
		function pageInit(){
			//创建jqGrid组件
			var colNames ;
			var colModel ;
			if(userType == 4){
				colNames = [ 'id','标题', '公司', '类型', '接收人', '发布时间', '操作'];
				colModel = [ 
                 {name : 'id',index : 'id',width : 100,hidden:true}, 
                 {name : 'title',index : 'title',width : 200, align:"center"}, 
                 {name : 'companyName',index : 'companyName',width : 200, align:"center"}, 
                 {name : 'type',index : 'type',width : 200, align:"center", formatter: changeType}, 
                 {name : 'name',index : 'name',width : 200, align:"center"},
                 {name : 'create_time',index : 'create_time',width : 200, align:"center", formatter:changeCreateTime}, 
                 {label: '操作', align: 'center', name: 'state', index: 'state', width: 200, edittype:"button", 
                	 formatter: logNoticeOpt }
               ];
			}else{
				colNames = [ 'id','标题', '类型', '接收人', '发布时间', '操作'];
				colModel = [ 
                 {name : 'id',index : 'id',width : 100,hidden:true}, 
                 {name : 'title',index : 'title',width : 200, align:"center"}, 
                 {name : 'type',index : 'type',width : 200, align:"center", formatter: changeType}, 
                 {name : 'name',index : 'name',width : 200, align:"center"},
                 {name : 'create_time',index : 'create_time',width : 200, align:"center", formatter:changeCreateTime}, 
                 {label: '操作', align: 'center', name: 'state', index: 'state', width: 200, edittype:"button", 
                	 formatter: logNoticeOpt }];
			}
			jQuery("#list2").jqGrid(
					{
		    	        url : path+'/QuanDiary/log/logNoticeListData.do',
		    	        datatype : "json",
		    	        colNames : colNames,
		    	        colModel : colModel,
		    	        rowNum : 20,
		    	        autowidth:true,
		    	        rownumbers: true,
		    	        height:'520px',
		    	        rowList : [ 10, 20, 50, 100],
		    	        pager : '#pager2',
		    	        sortname : 'id',
		    	        mtype : "post",
		    	        viewrecords : true,
		    	        sortorder : "desc",
		    	        caption : "日志通告"
					});
			/*创建jqGrid的操作按钮容器*/
			/*可以控制界面上增删改查的按钮是否显示*/
			jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
		}
		function changeType(val){
			switch (val) {
			case 0:
				return "<span>公司全体人员</span>";
				break;
			case 1:
				return "<span>部门全体人员</span>";
				break;
			case 2:
				return "<span>指定人员</span>";
				break;
			default:
				break;
			}
		}
		function changeCreateTime(val){
			return formatTime(val);
		}
		function logNoticeOpt(value, grid, rows, state){
			return "<button class='handle' onclick='show("+rows.id+")'>查看</button>&nbsp;<button class='handle' onclick='del("+rows.id+")'>删除</button>";
		}
		function show(id){
			$.ajax({
				url:path+"/QuanDiary/log/viewLogNotice.do?id="+id,
				type:"post",
				success:function(result){
					
					openWindow("查看日志通告", result);
				}
			});
		}
		function del(id){
			$.ajax({
				url:path+"/QuanDiary/log/delLogNotice.do?id="+id,
				type:"post",
				dataType:"json",
				success:function(result){
					
					alert(result.msg);
					reloadLogList();
				}
			});
		}
		function reloadLogList(){

            $("#list2").jqGrid('setGridParam',{
                datatype:'json',
                postData:{}, //发送数据
                page:1
            }).trigger("reloadGrid");
		}
		
		</script>
	</head>
	<body>

		<table id="list2"></table> 
		<div id="pager2"></div>
		<br>

		
	</body>

</html>