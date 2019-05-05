package chat.servive;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import chat.dao.PersonalDao;
import chat.entity.Personal;
import chat.util.ChatResult;
import chat.util.ChatUtil;

@Service("personalService")
public class PersonalServiceImpl implements PersonalService{

	@Resource
	PersonalDao dao;
	
	public ChatResult<Object> addPersonal(String name,String password) {
		ChatResult<Object> result = new ChatResult<Object>();
		Personal p = new Personal();
		
		Personal p1 = dao.check(name);
		if(p1!=null){
			result.setMsg("账号已存在！");
			result.setStatus(1);
			return result;
		}else{
			try{
				//加密密码
				String md5Password=ChatUtil.md5(password);
				p.setPersonal_password(md5Password);
				p.setPersonal_name(name);
				String id = ChatUtil.createId();
				p.setPersonal_id(id);
				dao.save(p);
				
				result.setData(p);
				result.setMsg("注册成功！");
				result.setStatus(0);
				return result;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	public ChatResult<Personal> login(String name,String password) {
		ChatResult<Personal> result = new ChatResult<Personal>();
		Personal p = dao.check(name);
		if(p==null){
			result.setMsg("账号不存在！");
			result.setStatus(1);
			return result;
		}
		try{
			String md5Password = ChatUtil.md5(password);
			if(!p.getPersonal_password().equals(md5Password)){
				result.setMsg("密码错误！");
				result.setStatus(2);
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//账号密码正确
		result.setStatus(0);
		result.setData(p);
		result.setMsg("登录成功！");
		
		return result;
	}

	public ChatResult<Personal> name(String personal_id) {
		ChatResult<Personal> result = new ChatResult<Personal>();
		if(personal_id!=null){
			Personal p = dao.name(personal_id);
			result.setMsg("查询成功!");
			result.setStatus(0);
			result.setData(p);
		}else{
			result.setMsg("查询失败！");
			result.setStatus(1);
		}
		return result;
	}

}
