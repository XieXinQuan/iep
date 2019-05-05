package chat.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.entity.Message;
import chat.servive.MessageService;
import chat.util.ChatResult;

@Controller
@RequestMapping("/message")
public class MessageController {
	@Resource
	MessageService service;
	
	@RequestMapping("/send.do")
	@ResponseBody
	public ChatResult<Message> execute(String personal_id,String message){
		ChatResult<Message> result = service.sendMessage(personal_id,message);
		return result;
	}
}
