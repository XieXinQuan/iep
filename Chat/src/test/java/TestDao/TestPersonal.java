package TestDao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chat.dao.PersonalDao;
import chat.entity.Personal;



public class TestPersonal {
	@Test
	public void testUserDao(){
		String [] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
		PersonalDao dao = ctx.getBean("personalDao",PersonalDao.class);
		Personal p = new Personal();
		p.setPersonal_id("2");
		p.setPersonal_name("2");
		p.setPersonal_password("123");
		dao.save(p);
		System.out.println(p);
	}
	@Test
	public void test1(){
		String [] conf = {"conf/spring-mvc.xml","conf/spring-mybatis.xml"};
		ApplicationContext ctx = new ClassPathXmlApplicationContext(conf);
		PersonalDao dao = ctx.getBean("personalDao",PersonalDao.class);
		Personal p = dao.login("ปรฬý");
		System.out.println(p);
	}
}
