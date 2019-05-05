package TestService;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chat.entity.Personal;
import chat.servive.PersonalService;
import chat.util.ChatResult;

public class TestPersonalService {
	
	PersonalService service;
	@Before
	public void init(){
		String [] conf = {"conf/spring-mybatis.xml","conf/spring-mvc.xml","conf/spring-transaction.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
		service = ctx.getBean("personalService",PersonalService.class);
	}
	@Test
	public void test1(){
		ChatResult<Object> result =  service.addPersonal("3", "3");
		System.out.println(result);
	}
	@Test
	public void test2(){
		
	}
	
}
