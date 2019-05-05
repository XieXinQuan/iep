<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
	#userTreeLeft {
		float:left;
		width:20%;
		height:600px;
	}
	#userManageright{
		float:left;
		width:78%;
		height:600px;
		
	}

    #rMenu {position:absolute; visibility:hidden; top:0; text-align: left;padding:4px;}
    #rMenu a{
        padding: 3px 15px 3px 15px;
        background-color:#cad4e6;
        vertical-align:middle;
    }

</style>
<script type="text/javascript">
	var rightId ;

	var log, className = "dark";


	var setting = {
        isSimpleData : true, //数据是否采用简单 Array 格式，默认false
        treeNodeKey : "id", //在isSimpleData格式下，当前节点id属性
        treeNodeParentKey : "pId", //在isSimpleData格式下，当前节点的父节点id属性
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
        callback :{
            onClick : function(event, treeId, treeNode, clickFlag) {  
                $.ajax({
                    url: path+"/QuanDiary/company/employeeInformation.do",//请求的action路径
                    data:{"id":treeNode.id},
                    error: function () {//请求失败处理函数
                        alert('请求失败');
                    },
                    success:function(data){
                    	//alert(treeNode.name);
                    	$("#userManageright").html(data);
                    }
                        
                    });
            },
            onRightClick : OnRightClick
        }
	};

	var zTree;
	var treeNodes;

	//初始化节点
	$(document).ready(function(){
	    $.fn.zTree.init($("#treeDemo"), setting, treeNodes);
	});
	
	function refreshNode() {
		/*根据 treeId 获取 zTree 对象*/
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	    zTree.reAsyncChildNodes(null, "refresh");
	}

	function OnRightClick(event, treeId, treeNode) {
		
		if(treeNode != null){
			rightId = treeNode.id;
			if(treeNode.isParent){
				if(treeNode.id == 0){
					$("#rightAddUser").hide();
				}else{
					$("#rightAddUser").show();
				}
				showRMenu("node", event.clientX, event.clientY);
			}
	
			
			
		}
	
	}
	//显示右键菜单
	function showRMenu(type, x, y) {
		
	    $("#rMenu").show();
	   	$("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"}); //设置右键菜单的位置、可见
	    $("body").bind("mousedown", onBodyMouseDown);
	}
	//隐藏右键菜单
	function hideRMenu() {
	    if (rMenu) $("#rMenu").css({"visibility": "hidden"}); //设置右键菜单不可见
	    $("body").unbind("mousedown", onBodyMouseDown);
	}
	//鼠标按下事件
	function onBodyMouseDown(event){
	    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
	    	$("#rMenu").css({"visibility" : "hidden"});
	    }
	}
	//右键添加员工
	function rightAddUser(){
		console.log(rightId);
	}
	function rightAddDept(){
		
		//rightId;
		var deptName = prompt("请输入你的名字","部门名称");
		if (deptName!=null && deptName!=""){
			$.ajax({

				url:path+"/QuanDiary/company/saveDept.do",
				type:"post",
				data:{"deptId":rightId,"deptName":deptName},
				dataType:"json",
				success:function(result){
					if(result.status == 1){
						alert("添加成功！");
						refreshNode();
					//closeWindow();
					}else{
						alert(result.msg);
					}
				}
			});
		}

	}
</script>
</head>
<body>

<div class="">
	<div id="userTreeLeft" class="zTreeDemoBackground">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div id="userManageright">
		
	</div>
</div>
<div id="rMenu" style="background-color:#CDBE70;">
	<u id="rightAddDept" class="handle" onclick="rightAddDept();">添加部门</u><br>
    <!--<input id="rightAddDept" type="button" value="添加部门" onclick="rightAddDept();"><br>
     <input id="rightAddUser" type="button" value="添加员工" onclick="rightAddUser();">  -->
</div>
</body>
</html>