<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	#companyInfor{
		display:none;
	}
	#userInfor{
		display:none;
	}
	
</style>
<script type="text/javascript">


	var id = ${id };

	function updateUserInfor(){

		$.ajax({

			url:path+"/QuanDiary/company/addEmployeeInformation.do",
			type:"post",
			data:{"userId":id},
			//dataType:"json",
			success:function(result){
				var name = $("#uName").text();

				openWindow("修改用户【"+name+"】信息",result);

			}
		});
	}


	function pageInit(){
		//创建jqGrid组件
		jQuery("#list2").jqGrid(
				{
	    	        url : path+'/QuanDiary/company/loadUserInfor.do?id='+id,
	    	        datatype : "json",
	    	        //colNames : [ 'id', '名字', '所在部门', 'status', '操作'],
	    	        colNames : [ 'id', '名字', '员工类型', '所在部门', '在职情况'],
	    	        colModel : [ 
	    	                     {name : 'id',index : 'id',width : 100,hidden:true}, 
	    	                     {name : 'displayName',index : 'displayName',width : 200, align:"center"}, 
	    	                     {name : 'userType',index : 'userType',width : 100, align:"center", formatter: changeUserType},
	    	                     {name : 'deptName',index : 'deptName',width : 100, align:"center"},
	    	                     {name : 'status',index : 'status',width : 100, align:"center", formatter: changeStatus}
	    	                 //    {name : 'status',index : 'status',width : 100, align:"center", formatter: changeStatus},
	    	                 //    {label: '操作', align: 'center', name: 'state', index: 'state', width: 100, edittype:"button", 
	    	                 //   	 formatter: function (value, grid, rows, state) { return "<button onclick='show("+rows.id+")'>查看</button>&nbsp;<button onclick='del("+rows.id+")'>删除</button>" } }
	    	                   ],
	    	        rowNum : 20,
	    	        rownumbers: true,
	    	        autowidth:true,
	    	        height:'560px',
	    	        rowList : [ 10, 20, 50, 100],
	    	        pager : '#pager2',
	    	        sortname : 'id',
	    	        mtype : "post",
	    	        viewrecords : true,
	    	        sortorder : "desc",
	    	        caption : "员工列表"
				});
		/*创建jqGrid的操作按钮容器*/
		/*可以控制界面上增删改查的按钮是否显示*/
		jQuery("#list2").jqGrid('navGrid', '#pager2', {search:false, edit : false,add : false,del : false});
	}
	function show(id){
		openWindow("查看模板");
	}
	function del(id){
		alert("del:"+id);
	}
	function changeUserType(cellvalue){
		switch (cellvalue) {
			case 2:
				return "<span>公司普通员工</span>";
				break;
			case 3:
				return "<span>公司管理员</span>";
				break;
			case 5:
				return "<span>部门管理员</span>";
				break;
		}

	}
	function changeStatus(cellvalue){
		switch (cellvalue) {
			case 1:
				return "<span style='color:green;'>在职</span>";
				break;
			case 11:
				return "<span style='color:red;'>离职</span>";
				break;
			case 13:
				return "<span style='color:#EE82EE;'>请假</span>";
				break;
			case 14:
				return "<span style='color:#EE82EE;'>出差</span>";
				break;
		}

	}
	$(function(){
		if(id > 0){
			loadUserInfor(id);
			$("#userInfor").show();
			$("#deptInfor").hide();
		}else {
			pageInit();
		}
	});
	function loadUserInfor(id){
		$.ajax({
			url:path+"/QuanDiary/company/loadUserInforByUserId.do",
			type:"post",
			data:{"id":id},
			dataType:"json",
			success:function(result){
				if(result.userType == 4){
					$("#udeptNameB").hide();
					$("#uPositionB").hide();
					$("#uWorkStatusB").hide();
				}else{
					$("#udeptName").text(result.deptName);
					$("#uWorkStatus").text(result.userStatus);
					$("#uPosition").text(result.position);
				}
				$("#uName").text(result.userName);

				$("#userInforName").text(result.userName);
				$("#hideInCompanyInfor").show();
				if(result.pi != null){
					var pi = result.pi;
					$("#userInforSex").text(pi.sex);
					$("#userInforCity").text(pi.city);
					$("#userInforDemocratic").text(pi.democratic);
					$("#userInforPhone").text(pi.phone);
					$("#userInforAdress").text(pi.adress);
					$("#userInforAge").text(pi.age);
					$("#userInforIdentityCard").text(pi.identity_card);
					$("#userInforHeight").text(pi.height);
					$("#userInforWeight").text(pi.weight);
					
				}
			}
		});
	}
</script>
</head>
<body>
<div id="companyInfor">
	<span>5</span>
</div>
<div id="deptInfor">
		<table id="list2"></table> 
		<div id="pager2"></div>
		
</div>
<div id="userInfor">
	
	<div>
	<b>姓名：</b><b id="uName"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
		<b id="udeptNameB">所在部门：</b><b id="udeptName"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<b id="uPositionB">职位：</b><b id="uPosition"></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<b id="uWorkStatusB">在职情况：</b><b id="uWorkStatus"></b>
	

	<input class='handle' type="button" value="修改" onclick="updateUserInfor();" style="float:right;">
	</div>

	<hr>
	<br>
	<div style="background-color: #EEE9E9"><span>【基本信息】</span></div>
	<hr>
	<table style="margin-left: 20px;">
		<tr>
			<td align='right'>姓名：</td>
			<td><span id="userInforName"></span></td>
			<td class="addTdWidth"></td>
			<td align='right'>性别：</td>
			<td><span id="userInforSex"></span></td>
		</tr>
		<tr>
			<td align='right'>籍贯：</td>
			<td><span id="userInforCity"></span></td>
			<td class="addTdWidth"></td>
			<td align='right'>民族：</td>
			<td><span id="userInforDemocratic"></span></td>
		</tr>
	</table>

	<br>
	<div style="background-color: #EEE9E9"><span>【个人信息】</span></div>
	<hr>
	<table style="margin-left: 20px;">
		<tr>
			<td align='right' bgcolor="#EBEBEB">手机号码：</td>
			<td bgcolor="#EBEBEB" bgcolor="#EBEBEB"><span id="userInforPhone"></span></td>
			<td class="addTdWidth"></td>
			<td align='right' bgcolor="#EBEBEB">家庭住址：</td>
			<td bgcolor="#EBEBEB"><span id="userInforAdress"></span></td>
		</tr>
		<tr>
			<td align='right' bgcolor="#EBEBEB">年&nbsp;&nbsp;龄：</td>
			<td bgcolor="#EBEBEB"><span id="userInforAge"></span></td>
			<td class="addTdWidth"></td>
			<td align='right' bgcolor="#EBEBEB">身 份 证：</td>
			<td bgcolor="#EBEBEB"><span id="userInforIdentityCard"></span></td>
		</tr>
		<tr>
			<td align='right' bgcolor="#EBEBEB">身&nbsp;&nbsp;高：</td>
			<td bgcolor="#EBEBEB"><span id="userInforHeight"></span></td>
			<td class="addTdWidth"></td>
			<td align='right' bgcolor="#EBEBEB">体&nbsp;&nbsp;重：</td>
			<td bgcolor="#EBEBEB"><span id="userInforWeight"></span></td>
		</tr>
	</table>
</div>
</body>
</html>