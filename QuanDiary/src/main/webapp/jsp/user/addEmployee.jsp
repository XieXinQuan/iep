<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

var selectDeptId;
var zTree;
var setting = {
        isSimpleData : true, //数据是否采用简单 Array 格式，默认false
        treeNodeKey : "id", //在isSimpleData格式下，当前节点id属性
        treeNodeParentKey : "pId", //在isSimpleData格式下，当前节点的父节点id属性
        showLine : true, //是否显示节点间的连线
        async:{
            enable: true,
             url: path+"/QuanDiary/company/loadDeptData.do",
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
		callback:{
	        onCheck :function(e, treeId, treeNode){
	        	selectDeptId = treeNode.id;
	        	$("#selectDeptName").val(treeNode.name);
	        	$("#selectDeptTree").hide();
	        }
		}
		
    };

	$(function(){
		var url = path + "/QuanDiary/user/download.do";
		document.getElementById("downAddUserModule").action= url;
		url = path +"/QuanDiary/user/addEmployeeByFile.do";
		document.getElementById("sumbitFileAddUser").action= url;
		
	});
	$(document).ready(function(){
		var zNodes ;
		$.fn.zTree.init($("#selectDeptTree"), setting, zNodes);	
	});
	function confirmSumbit(){
		
		if(typeof(selectDeptId) == 'undefined'){
			alert("未选择部门！");
			return;
		}
		var loginName = $("#loginName").val();
		var displayName = $("#displayName").val();
		if(displayName == null || displayName == ""){
			
			alert("姓名不能为空！");
			return;
		}else if(loginName == null || loginName == ""){
			alert("账号不能为空！");
			return;
		}else if(loginName.length < 4){
			alert("账号不能小于四位数！");
			return;
		}else if (loginName.length > 16) {
			alert("账号不能大于十六位数！");
			return;
		}else{
			
			$.ajax({

				url:path+"/QuanDiary/user/addEmployeeByManager.do",
				type:"post",
				data:{"loginName":loginName, "displayName":displayName, "deptId":selectDeptId},
				//dataType:"json",
				success:function(result){
					alert(result.msg);
				}
			});
		}
	}
	function addEmployeeBatch(){
		$.ajax({
			url:path+"/QuanDiary/user/addEmployeeBatch.do",
			type:"post",
			data:{},
			//dataType:"json",
			success:function(result){
				
			}
		});
	}
	function showBatchAddEmployee(){
		$("#showBatchAddEmployee").show();
	}
	function showSelectDeptTree(){
		$("#selectDeptTree").show();
	}
</script>
</head>
<body>
		<div>
		<table>
			<tr>
				<td style="width:100px;">账&nbsp&nbsp号：</td>
				<td><input id="loginName" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" type="text" style="width:200px;"></td>
				<td><span style="color:red;">提示：为避免登录名被占用，建议使用手机号码</span></td>
			</tr>
			<tr>
				<td>姓&nbsp&nbsp名：</td>
				<td><input id="displayName" type="text" style="width:200px;"></td>
				<td></td>
			</tr>
			<tr>
				<td>密&nbsp&nbsp码：</td>
				<td><input readonly="readonly" placeholder="初始密码默认为：123456" style="width:200px;"></td>
				<td></td>
			</tr>
			<tr>
				<td>部&nbsp&nbsp门：</td>
				<td>
					<input id="selectDeptName" type="text" readonly="readonly"><input class='handle' type="button" value="选择" onclick="showSelectDeptTree();">
					<div id="selectDeptTree" class="ztree" style="overflow:auto;z-index:500;display:none;background-color:#FAFAD2;height:100px;width:200px;"></div>
				</td>
				<td></td>
			</tr>
			<tr style="height:50px;">
				<td colspan="2" ><div style="width:100%;height:100%;text-align:center;"><button class='handle' type="button" onclick="confirmSumbit();">提交</button></div></td>
				<td></td>
			</tr>
		</table>
		
	<div>
	<hr>
	<a href="javascript:void(0);" onclick="showBatchAddEmployee();">需要大批量的导入？</a>
	<div id="showBatchAddEmployee" style="display:none;">
		<h3>注：通过上传excel文件来创建时，需要注意以下几点：</h3>
		<p>1.第一列为账号，建议为手机号码，避免账号被占用。</p>
		<p>2.第二列为姓名。</p>
		
		<br>
		    <form id="sumbitFileAddUser" action="http://localhost:8080/QuanDiary/user/addEmployeeByFile.do"
	        method="post" enctype="multipart/form-data" target="_blank" >
	        选择文件:<input class='handle' type="file" name="file" width="120px"> 
	        <input class='handle' type="submit" value="上传">
	    </form>
	    
	    
	    <form id="downAddUserModule" action="http://localhost:8080/QuanDiary/user/download.do"
	        method="get">
	        <span>模板下载：</span><input class='handle' type="submit" value="下载">
	        
	    </form>
	    <!-- <input id="addEmployeeBatch" type="button" value="批量添加" onclick="addEmployeeBatch();"/> -->
	</div>

</body>
</html>