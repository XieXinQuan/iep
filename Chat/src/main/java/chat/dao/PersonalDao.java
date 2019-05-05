package chat.dao;


import chat.entity.Personal;

public interface PersonalDao {
	//注册
	public void save(Personal p);
	//登录
	public Personal login(String id);
	
	public Personal check(String name);

	public Personal name(String personal_id);
	
	

}
