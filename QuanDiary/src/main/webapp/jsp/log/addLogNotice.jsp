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
		callback:{
	        onCheck :function(e, treeId, treeNode){
	        	selectDeptId = treeNode.id;
	        	if(treeNode.id == -1){
	        		alert("此部门不可被选择！");
	        		return;
	        	}
	        	$("#selectDeptName").val(treeNode.name);
	        	$("#selectDeptTree").hide();
	        }
		}
		
    };

	$(document).ready(function(){
		var zNodes ;
		$.fn.zTree.init($("#selectDeptTree"), setting, zNodes);	
	});
	function confirmSumbit(){
		
		if(typeof(selectDeptId) == 'undefined'){
			alert("未选择部门！");
			return;
		}
		var logNoticeTitle = $("#logNoticeTitle").val();
		var stem = CKEDITOR.instances.editor.getData();
		if(logNoticeTitle == null || logNoticeTitle == ""){
			
			alert("标题不能为空！");
			return;
		}else if(stem == null || stem == ""){
			alert("通告不能为空！");
			return;
		}else{
			
			$.ajax({

				url:path+"/QuanDiary/log/saveLogNotice.do",
				type:"post",
				data:{"title":logNoticeTitle, "content":stem, "id":selectDeptId},
				dataType:"json",
				success:function(result){
					alert(result.msg);
				}
			});
		}
	}
	function showSelectDeptTree(){
		$("#selectDeptTree").show();
	}
</script>
</head>
<body>
	<div>
	<button class='handle' type="button" onclick="confirmSumbit();">提交</button><br><br>
		<table>
			<tr>
				<td style="width:100px;"><b>标&nbsp&nbsp题：</b></td>
				<td><input id="logNoticeTitle"  type="text" style="width:200px;"></td>
				<td></td>
			</tr>
			<tr>
				<td><b>部门/人员：</b></td>
				<td>
					<input id="selectDeptName" type="text" readonly="readonly"><input class='handle' type="button" value="选择" onclick="showSelectDeptTree();">
					<div id="selectDeptTree" class="ztree" style="overflow:auto;z-index:500;display:none;background-color:#FAFAD2;height:100px;width:200px;"></div>
				</td>
				<td><span style="color:red;">提示：可选择公司全体/部门/指定人员</span></td>
			</tr>

		</table>
		
	</div>
	<br>
	<b>通告内容：</b><br>
	<div id="editor">
       		
    </div>
<script>

    CKEDITOR.replace( 'editor' );


        $("#one").click(function () {
        
        var stem = CKEDITOR.instances.editor.getData();
        	alert(""+stem);
        });



</script>


</body>
</html>