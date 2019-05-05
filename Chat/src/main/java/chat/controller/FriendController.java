package chat.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.entity.Friend;
import chat.entity.Personal;
import chat.servive.FriendService;
import chat.util.ChatResult;

@Controller
@RequestMapping("/friend")
public class FriendController {
	@Resource
	FriendService service;
	
	@RequestMapping("/loadConversation.do")
	@ResponseBody
	public ChatResult<List<Friend>> execute1(String personal_id){
		ChatResult<List<Friend>> result = service.loadFriend1(personal_id);
		System.out.println(result);
		return result;
	}
	@RequestMapping("/search.do")
	@ResponseBody
	public ChatResult<Personal> execute2(String personal_id,String search_name){
		ChatResult<Personal> result = service.search(personal_id,search_name);
		return result;
	}
	@RequestMapping("/agree.do")
	@ResponseBody
	public ChatResult<Friend> execute3(String personal_id,String friend_id){
		ChatResult<Friend> result = service.addFriend(personal_id, friend_id);
		return result;
	}
	
}
