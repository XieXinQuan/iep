package chat.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import chat.entity.Personal;
import chat.servive.PersonalService;
import chat.util.ChatResult;
import event.eventListener;
import event.loginEvent;

@Controller
@RequestMapping("/personal")
public class PersonalController {
	@Resource
	PersonalService service;
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@RequestMapping("/register.do")
	@ResponseBody
	public ChatResult<Object> execute1(String name,String password){
		ChatResult<Object> result = service.addPersonal(name, password);
		return result;
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public ChatResult<Personal> execute2(String name,String password){
		System.out.println(name+"上线！");
		System.out.println("......");
		ChatResult<Personal> result = service.login(name, password);
		applicationEventPublisher.publishEvent(new loginEvent(name));
		return result;
	}
	@RequestMapping("/name.do")
	@ResponseBody
	public ChatResult<Personal> execute3(String personal_id){
		ChatResult<Personal> result = service.name(personal_id);
		return result;
	}
}
