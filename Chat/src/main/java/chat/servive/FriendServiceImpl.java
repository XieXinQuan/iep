package chat.servive;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import chat.dao.FriendDao;
import chat.dao.PersonalDao;
import chat.entity.Friend;
import chat.entity.Personal;
import chat.util.ChatResult;

@Service("friendService")
public class FriendServiceImpl implements FriendService{
	
	@Resource
	FriendDao dao;
	@Resource
	PersonalDao pdao;
	
	public ChatResult<Friend> addFriend(String personal_id, String friend_id) {
		ChatResult<Friend> result = new ChatResult<Friend>();
		
		Friend f1 = new Friend();
		f1.setPersonal_id(personal_id);
		f1.setFriend_id(friend_id);
		f1.setFriend_name(pdao.name(friend_id).getPersonal_name());
		dao.addFriend(f1);
		
		Friend f2 = new Friend();
		f2.setPersonal_id(friend_id);
		f2.setFriend_id(personal_id);
		f2.setFriend_name(pdao.name(friend_id).getPersonal_name());
		dao.addFriend(f2);
		
		result.setMsg("互加好友成功！");
		result.setStatus(0);
		result.setData(f1);
		
		return result;
	}

	public ChatResult<List<Friend>> loadFriend1(String personal_id) {
		ChatResult<List<Friend>> result = new ChatResult<List<Friend>>();
		List<Friend> list = dao.loadFriend(personal_id);
		result.setData(list);
		result.setMsg("查询会话好友成功！");
		result.setStatus(0);
		
		return result;
	}

	public ChatResult<Personal> search(String personal_id,String search_name) {
		ChatResult<Personal> result = new ChatResult<Personal>();
		Personal p = dao.search(search_name);
		Personal p1 = pdao.name(personal_id);
		List<Friend> list = dao.loadFriend(personal_id);
		//判断是否为好友
		for(Friend f:list){
			if(f.getFriend_name().equals(search_name)){
				result.setStatus(3);
				result.setMsg("你们已经是好友！");
				return result;
			}
		}
		if(p==null){
			result.setStatus(1);
			result.setMsg("账号不存在！");
		}else if(p1.getPersonal_name().equals(search_name)){
			result.setStatus(2);
			result.setMsg("不能添加自己！");
		}else{
			result.setStatus(0);
			result.setMsg("查找成功！");
			result.setData(p);
		}
		return result;
	}

	

	

	
	

}
