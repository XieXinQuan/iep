<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ page import="QuanDiary.Util.enumUtil.CommonStatus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

	<style type="text/css">
		#showTree {position:absolute; visibility:hidden; top:0; text-align: left;padding:4px;}
	</style>
		
		<!-- 本页面初始化用到的js包，创建jqGrid的代码就在里面 
		<script type="text/javascript" src="../../js/index.js"></script>-->
		<script type="text/javascript">
		
		
		var selectDeptId = 0;
		var zTree;
		var setting = {
		        isSimpleData : true, //数据是否采用简单 Array 格式，默认false
		        treeNodeKey : "id", //在isSimpleData格式下，当前节点id属性
		        treeNodeParentKey : "pId", //在isSimpleData格式下，当前节点的父节点id属性
		        multiselect: true,
		        showLine : true, //是否显示节点间的连线
		        async:{
		            enable: true,
		             url: path+"/QuanDiary/company/loadDeptData1.do",
		           autoParam: ["id"]
		        },
		        data: {
		            simpleData: {                
		                enable: true,            // 简单数据模式
		                idKey: "id",            
		                pIdKey: "pId",    
		                rootPId: 0              
		            }
		        },

		        view:{showIcon: false},
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				multiselect: true,  
				
				callback:{
			        onCheck :function(e, treeId, treeNode){
			        	selectDeptId = treeNode.id;
			        	$("#selectOtherUserName").val(treeNode.name);
			        	$("#showTree").css({"visibility" : "hidden"});

			        	var data = {"id":treeNode.id};
			        	/*
			        	if($("#cradListSearchTime").val() != null && $("#cradListSearchTime").val() != ""){
			        		data = {"id":treeNode.id, "time":$("#cradListSearchTime").val()};
			        	}else{
			        		data = {"id":treeNode.id};
			        	}
			        	
			        	$("#cradListSearchTime").val();
			        	*/
			            $("#list2").jqGrid('setGridParam',{
			                datatype:'json',
			                postData:data, //发送数据
			                page:1
			            }).trigger("reloadGrid");
			        }
				}
				
		    };
		$(document).ready(function(){
			var zNodes ;
			$.fn.zTree.init($("#selectDeptTree"), setting, zNodes);	
		});
		var listType = ${type};
		var userType = ${userType };
		
		$(function(){
			if(listType != 0) {
				$("#otherUserDiaryQueryDate").hide();
				$("#otherUserDiaryQueryTime").hide();
			}
			
			pageInit();
		});
		function pageInit(){
			//创建jqGrid组件
			var colNames;
			var colModel;
			if(userType == 4){
				colNames = ['id', '标题', '作者','所在公司','所在部门','被阅读总数','被点赞总数', '是否已读','状态','创建时间','操作'];
				colModel = [ 
                 {name : 'id',index : 'id',width : 100,hidden:true}, 
                 {name : 'title',index : 'title',width : 150, align:"center"}, 
                 {name : 'userName',index : 'userName',width : 80, align:"center"},
                 {name : 'companyName',index : 'companyName',width : 150, align:"center"}, 
                 {name : 'deptName',index : 'deptName',width : 80, align:"center"}, 
                 {name : 'logReadNum',index : 'logReadNum',width : 80, align:"center"}, 
                 {name : 'logLikeNum',index : 'logLikeNum',width : 80, align:"center"}, 
                 {name : 'isRead',index : 'isRead',width : 80, align:"center", formatter: formatterReadStatus}, 
                 {name : 'status',index : 'status',width : 80, align:"center", formatter: formatterStatus}, 
                 {name : 'createTime',index : 'createTime',width : 150, align:"center", formatter: changeCreateTime},
                 {label: '操作', align: 'center', name: 'state', index: 'state', width: 200, edittype:"button", 
                	 formatter: logListOpt }
               ];
			}else{
				colNames = ['id', '标题', '模板名称','作者','所在部门','被阅读总数','被点赞总数', '是否已读','创建时间','操作'];
				colModel = [ 
                 {name : 'id',index : 'id',width : 100,hidden:true}, 
                 {name : 'title',index : 'title',width : 150, align:"center"}, 
                 {name : 'moduleTitle',index : 'moduleTitle',width : 150, align:"center"}, 
                 {name : 'userName',index : 'userName',width : 80, align:"center"},

                 {name : 'deptName',index : 'deptName',width : 80, align:"center"}, 
                 {name : 'logReadNum',index : 'logReadNum',width : 80, align:"center"}, 
                 
                 {name : 'logLikeNum',index : 'logLikeNum',width : 80, align:"center"}, 
                 {name : 'isRead',index : 'isRead',width : 80, align:"center", formatter: formatterReadStatus}, 
                 {name : 'createTime',index : 'createTime',width : 150, align:"center", formatter: changeCreateTime},
                 {label: '操作', align: 'center', name: 'state', index: 'state', width: 200, edittype:"button", 
                	 formatter: logListOpt }
               ];
			}
			var url;
			if(listType == 0){
				
				url = path+"/QuanDiary/log/loadOtherUserLogListData.do";
			}else if(listType == 1){
				url = path+"/QuanDiary/log/loadOtherUserLogListData.do?time="+formatDate(new Date().getTime());
			}else if(listType == 2){
				var urlAppend = "?startTime="+getWeekFirstDay()+" 00:00&endTime="+getWeekLastDay()+" 23:59";
				url = path+"/QuanDiary/log/loadOtherUserLogListData.do"+urlAppend;
			}
			jQuery("#list2").jqGrid(
					{
		    	        url : url,
		    	        datatype : "json",
		    	        colNames : colNames,
		    	        colModel : colModel,
		    	        rowNum : 20,
		    	        autowidth:true,
		    	        rownumbers: true,
		    	        height: '500px',
		    	        rowList : [ 10, 20, 50, 100],
		    	        //multiselect: true,
		    	        pager : '#pager2',
		    	        sortname : 'id',
		    	        mtype : "post",
		    	        viewrecords : true,
		    	        sortorder : "desc",
		    	        caption : "其他员工日志列表"
					});
			/*创建jqGrid的操作按钮容器*/
			/*可以控制界面上增删改查的按钮是否显示*/
			jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
		}
		function logListOpt(value, grid, rows, state) { 
			var html = "";
			if(userType == 2 || userType == 3){
				html += "<img src='../images/viewDiary.png' style='height:21px;width:38px;' class='handle' onclick='show("+rows.id+")'/>&nbsp;";
				//html += "<button class='handle' onclick='show("+rows.id+")'>查看</button>&nbsp;";
				html += "<img src='../images/diaryLike.png' style='height:21px;width:38px;' class='handle' onclick='diaryLike("+rows.id+")'/>&nbsp;";
				//html += "<button class='handle' onclick='diaryLike("+rows.id+")'>点赞</button>&nbsp;";
				html += "<button class='handle' onclick='diaryComment("+rows.id+")'>互动</button>&nbsp;";
			}
			if(userType == 3){
				html += "<button class='handle' onclick='del1("+rows.id+", 1)'>撤销</button>&nbsp;";
				html += "<button class='handle' onclick='del1("+rows.id+", 2)'>拒绝</button>&nbsp;";
				
			}
			if(userType == 4){
				html += "<button class='handle' onclick='show("+rows.id+")'>查看</button>&nbsp;";
				html += "<button class='handle' onclick='del1("+rows.id+", 1)'>撤销</button>&nbsp;";
				html += "<button class='handle' onclick='del1("+rows.id+")'>删除</button>&nbsp;";
			}
   			return html; 
   		}
		function show(id){
			
			if(id != null){
				
				$.ajax({
					url:path+"/QuanDiary/log/viewDiary.do?id="+id,
					type:"post",
					success:function(result){
						
						openWindow("查看日志", result);
						//$("#list2").jqGrid('resetSelection');
						cradListSearchTime();
					}
				});
			}
		}

		function del1(id, status){
			if(typeof(status) == 'undefined') {
				data = {"id": id};
			}else{
				data = {"id":id, "status": status};
			}
				

			if(confirm("您确定要进行此操作吗？")){
				$.ajax({
					url:path+"/QuanDiary/log/delDiary.do",
					type:"post",
					data:data,
					dataType:"json",
					success:function(result){

						alert(result.msg);
						cradListSearchTime();
					}

				});
			}

		}
		
		function diaryLike(id){
			$.ajax({
				url:path+"/QuanDiary/log/diaryLike.do",
				type:"post",
				data:{"id":id},
				dataType:"json",
				success:function(result){
					
					alert(result.msg);
					cradListSearchTime();
				}
			});
		}
		function diaryComment(id){
			$.ajax({
				url:path+"/QuanDiary/log/viewDiaryComment.do",
				type:"post",
				data:{"id":id},
				//dataType:"json",
				success:function(result){
					//alert(2);
					openWindow("日志评论",result);
				}
			});
		}
		function formatterReadStatus(val){
			if(val > 0){
				return "<span style='color:green;'>已读</span>";
			}else{
				return "<span style='color:#EE82EE;'>未读</span>";
			}
		}
		function changeCreateTime(cellvalue){
			return formatTime(cellvalue*1000);
		}
		function del(id){
			if(confirm("您确定要删除吗？")){
				$.ajax({
					url:path+"/QuanDiary/log/delDiary.do?id="+id,
					type:"post",
					dataType:"json",
					success:function(result){
						if(result.status == 1){
							alert("删除成功！");
							cradListSearchTime();
						}
					}
				});
			}
			

		}
		function formatterStatus(cellvalue){
			switch (cellvalue) {
			case 1:
				return "<span style='color:green;'>正常</span>";
				break;
			case 22:
				return "<span style='color:#EE82EE;'>已被管理员撤销</span>";
				break;
			case 23:
				return "<span style='color:red;'>已被管理员拒绝</span>";
				break;
			case 91:
				return "<span style='color:red;'>已停用</span>";
				break;
			}

		}
		
		function reloadLogList(){
			

            $("#list2").jqGrid('setGridParam',{
                datatype:'json',
                postData:{}, //发送数据
                page:1
            }).trigger("reloadGrid");
		}
		function showSelectUserTree(event){
			
		    $("#showTree").show();
		   	$("#showTree").css({"top":event.clientY+"px", "left":event.clientX+"px", "visibility":"visible"}); //设置右键菜单的位置、可见
		    $("body").bind("mousedown", onBodyMouseDown);
		}
		//鼠标按下事件
		function onBodyMouseDown(event){
		    if (!(event.target.id == "showTree" || $(event.target).parents("#showTree").length>0)) {
		    	$("#showTree").css({"visibility" : "hidden"});
		    }
		}
		function cradListSearchTime(){
            $("#list2").jqGrid('setGridParam',{
                datatype:'json',
                //postData:{"time":time, "id":selectDeptId}, //发送数据
                postData:{"id":selectDeptId, "time":$("#cradListSearchTime").val()}, //发送数据
                page:1
            }).trigger("reloadGrid");
		}
		function diaryExport(){
			var ids = $("#list2").jqGrid('getGridParam','selarrrow');
			for(var i = 0;i<ids.length;i++){
				console.log(ids[i]);
			}
		}
	    jeDate({
			dateCell:"#searchListSearchStartTime",
			format:"YYYY-MM-DD",
			//isinitVal:true,
			isTime:false //isClear:false,
			//minDate:"2014-09-19 00:00:00",
			//okfun:function(val){alert(val)}
		});
	    jeDate({
			dateCell:"#searchListSearchEndTime",
			format:"YYYY-MM-DD",
			//isinitVal:true,
			isTime:false //isClear:false,
			//minDate:"2014-09-19 00:00:00",
			//okfun:function(val){alert(val)}
		});
	    function searchDiaryByTime(){
	    	var startTime = $("#searchListSearchStartTime").val();
	    	var endTime = $("#searchListSearchEndTime").val();
	    	if(startTime == null || startTime == "") {
	    		alert("开始时间不能为空！");
	    		return;
	    	}
	    	if(endTime == null || endTime == ""){
	    		alert("截止时间不能为空！");
	    		return;
	    	}
	    	var startTimeLong = new Date(startTime+" 00:00");
	    	var endTimeLong = new Date(endTime+" 00:00");
	    	if(endTimeLong < startTimeLong){
	    		alert("开始时间不得小于结束时间！");
	    		return;
	    	}
			var time = $("#cradListSearchTime").val();
			if(time == "") time = "";
            $("#list2").jqGrid('setGridParam',{
                datatype:'json',
                postData:{"time": time,
                	"startTime":startTime+" 00:00",
                	"endTime":endTime+"23:59",
                	"id":selectDeptId}, //发送数据
                page:1
            }).trigger("reloadGrid");

	    }
	    
	    $(function (){
	    	var url = path + "/QuanDiary/log/diaryExport.do";
			document.getElementById("diaryExport").action= url;
	    });
	    function sumbitDiaryExport(){
	    	var startTime = $("#searchListSearchStartTime").val();
	    	var endTime = $("#searchListSearchEndTime").val();
	    	var time = $("#cradListSearchTime").val();
			
			if(listType == 1) time = formatDate(new Date().getTime());
			//console.log(formatDate(new Date().getTime()));
			var url = path + "/QuanDiary/log/diaryExportExcel.do?time="+time+"&id="+selectDeptId;

			if(listType == 0){
				if(startTime != "") url += "&startTime="+startTime+" 00:00";
		    	if(endTime != "") url += "&endTime="+endTime+" 23:59";
			}else if(listType == 2){
				url += "&startTime="+getWeekFirstDay()+" 00:00";
				url += "&endTime="+getWeekLastDay()+" 23:59";
			}

			document.getElementById("diaryExport").action= url;
	    	var form = document.getElementById('diaryExport');
	    	//再次修改input内容

	    	form.submit();
	    }
		</script>
	</head>
	<body>
		<div id="otherUserDiaryQuery" style="height:30px;background-color: #D1EEEE;" >
			
			<span id="otherUserDiaryQueryDate">请选择查询日期：<input type="date" id="cradListSearchTime" onchange="cradListSearchTime();"></span>
			&nbsp;请选择人员：<input id="selectOtherUserName" type="text" readonly="readonly"><input class='handle' type="button" value="选择" onclick="showSelectUserTree(event);">
			<span id="otherUserDiaryQueryTime">
			&nbsp;指定时间：<input class="datainp" id="searchListSearchStartTime" type="text" value="" readonly>
			-
			<input class="datainp" id="searchListSearchEndTime" type="text" value="" readonly>
			<input class="handle" value="确定" onclick="searchDiaryByTime();" type="button">
			</span>
			<form id="diaryExport" action="http://localhost:8080/QuanDiary/log/diaryExport.do"
	        	method="post" style="float:right;">
	        	<input class="handle" value="导出" type="button" onclick="sumbitDiaryExport();">
	        
	    	</form>
		</div>
		<!-- <div style="height:200px;background-color: #FFF0F5;overflow:auto;">
			指定人员：<div id="selectLoadUser" class="ztree" style="overflow:auto;width:100;background-color: #EEE8CD"></div>
		</div>
		<hr> -->
		<table id="list2"></table> 
		<div id="pager2"></div>
		<br>
		<div id="showTree">
			<div id="selectDeptTree" class="ztree" style="overflow:auto;z-index:500;background-color:#FAFAD2;"></div>
		</div>
		
		
	</body>

</html>