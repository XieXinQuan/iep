package TestService;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chat.entity.Friend;
import chat.servive.FriendService;
import chat.util.ChatResult;

public class TestFriendService {
	FriendService service;

	@Before
	public void init(){
		String [] conf = {"conf/spring-mybatis.xml","conf/spring-mvc.xml","conf/spring-transaction.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
		service = ctx.getBean("friendService",FriendService.class);
	}
	@Test
	public void test1(){
		ChatResult<List<Friend>> result = service.loadFriend1("01524f8bff634fa1a77b38aef2fb8afb");
		System.out.println(result);
	}
}