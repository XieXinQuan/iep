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
		    	        url : path+'/QuanDiary/log/logStatisticalData.do',
		    	        datatype : "json",
		    	        colNames : [ '名字', '所在部门', '日志条数', '阅读条数', '点赞条数', '评论条数'],
		    	        colModel : [ 
		    	                     //{name : 'id',index : 'id',width : 100,hidden:true}, 
		    	                     {name : 'displayName',index : 'displayName',width : 200, align:"center"}, 
		    	                     {name : 'deptName',index : 'deptName',width : 200, align:"center"}, 
		    	                     {name : 'logNum',index : 'logNum',width : 200, align:"center"}, 
		    	                     {name : 'logReadNum',index : 'logReadNum',width : 200, align:"center"}, 
		    	                     {name : 'logLikeNum',index : 'logLikeNum',width : 200, align:"center"}, 
		    	                     {name : 'logCommentNum',index : 'logCommentNum',width : 200, align:"center"}, 
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
		    	        caption : "日志统计"
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

		function reloadLogList(year, month){
			//console.log();
			var time = "";
			if(month<10){
				time = ""+year+"-0"+month;
			}else{
				time = ""+year+"-"+month;
			}
			//console.log(time);
            $("#list2").jqGrid('setGridParam',{
                datatype:'json',
                postData:{"time":time}, //发送数据
                page:1
            }).trigger("reloadGrid");
		}
		
		</script>
	</head>
	<body>
		<div id="logStatistical" style="height:20px;background-color: #D1EEEE;" >
			年份：
			<select id="selectYearOption"  class='handle'>

			</select>&nbsp;
			月份：
			<select id="selectMonthOption"  class='handle'>

			</select>
		</div>
		<table id="list2"></table> 
		<div id="pager2"></div>
		<br>

		
	</body>

</html>