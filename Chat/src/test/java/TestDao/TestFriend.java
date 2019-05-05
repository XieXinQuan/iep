package TestDao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chat.dao.FriendDao;
import chat.entity.Friend;

public class TestFriend {
	FriendDao dao;
	@Before
	public void init(){
		String [] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
		dao = ctx.getBean("friendDao",FriendDao.class);
	}
	@Test
	public void test1(){
		List<Friend> list = dao.loadFriend("01524f8bff634fa1a77b38aef2fb8afb");
		for(Friend f:list){
			System.out.println(f.getFriend_name());
		}
	}
}
