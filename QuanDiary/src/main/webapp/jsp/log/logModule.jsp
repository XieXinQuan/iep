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
		    	        url : path+'/QuanDiary/log/loadLogModuleData.do',
		    	        datatype : "json",
		    	        colNames : [ 'id', '标题', 'status', '操作'],
		    	        colModel : [ 
		    	                     {name : 'id',index : 'id',width : 100,hidden:true}, 
		    	                     {name : 'title',index : 'title',width : 200, align:"center"}, 
		    	                     {name : 'status',index : 'status',width : 100, align:"center", formatter: changeStatus},
		    	                     {label: '操作', align: 'center', name: 'state', index: 'state', width: 50, edittype:"button", 
		    	                    	 formatter: function (value, grid, rows, state) { return "<button class='handle' onclick='show("+rows.id+")'>查看</button>&nbsp;<button class='handle' onclick='del("+rows.id+")'>删除</button>" } }
		    	                   ],
		    	        rowNum : 10,
		    	        rownumbers: true,
		    	        autowidth:true,
		    	        height:'520px',
		    	        rowList : [ 10, 20, 50, 100],
		    	        pager : '#pager2',
		    	        sortname : 'id',
		    	        mtype : "post",
		    	        viewrecords : true,
		    	        sortorder : "desc",
		    	        caption : "日志模板"
					});
			/*创建jqGrid的操作按钮容器*/
			/*可以控制界面上增删改查的按钮是否显示*/
			jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
		}
		function show(id){
			$.ajax({
				url:path+"/QuanDiary/log/viewDiaryModule.do",
				type:"post",
				data:{"id":id},
				//dataType:"json",
				success:function(result){
					openWindow("查看模板", result);
				}
			});
			
		}
		function del(id){
			if(confirm("您确定要删除吗？")){
				$.ajax({
					url:path+"/QuanDiary/log/delLogModule.do",
					type:"post",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						if(result.status == 1){
							alert("删除成功！");
				            $("#list2").jqGrid('setGridParam',{
				                datatype:'json',
				                postData:{}, //发送数据
				                page:1
				            }).trigger("reloadGrid");
						}
					}
				});
			}
		}
		function changeStatus(cellvalue){
			switch (cellvalue) {
				case 1:
					return "<span style='color:green;'>使用中</span>";
					break;
				case 91:
					return "<span style='color:red;'>已停用</span>";
					break;
			}

		}
		</script>
	</head>
	<body>
		<table id="list2"></table> 
		<div id="pager2"></div>
		<br>

		
	</body>

</html>