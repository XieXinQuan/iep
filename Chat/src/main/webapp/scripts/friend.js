function name(){
	if(personal_id!=null){
		$.ajax({
			url:path+"/personal/name.do",
			type:"post",
			data:{"personal_id":personal_id},
			dataType:"json",
			success:function(result){
				var personal_name = result.data.personal_name;
				$("#personal_name").html(personal_name);
			},
			error:function(){
				alert("加载昵称失败！");
			}
		});
	}
}
//登录成功加载好友
function load_con(){
	//var personal_id = getCookie("personal_id");
	//var personal_name = getCookie("personal_name");
	
	//$("#personal_name").html(personal_name);
	if(personal_id==null){
		alert("请重新登录！");
		window.location="login.html";
	}else{
		$.ajax({
			url:path+"/friend/loadConversation.do",
			type:"post",
			data:{"personal_id":personal_id},
			dataType:"json",
			success:function(result){
				//清除原列表信息
				$("#friends_a").empty();
				for(var i=0;i<result.data.length;i++){
					var friend_name=result.data[i].friend_name;
					//alert(friend_name);
					var friend_id=result.data[i].friend_id;
					var friend_conversation='';
					friend_conversation+='<li class="list-group-item"><span class="badge new">new</span>'+friend_name+'</li>';
					//将sli字符串转换成jQuery对象li元素
					var $friend_conversation=$(friend_conversation);
					//将bookId值与jQuery对象绑定
					var o = {"friend_id":friend_id,"friend_name":friend_name};
					$friend_conversation.data(o);
					//将li元素添加到笔记本ul列表区
					$("#friends_li").append($friend_conversation);
				}
			},
			error:function(){
				alert("加载好友失败！");
			}
		});
	}
};
