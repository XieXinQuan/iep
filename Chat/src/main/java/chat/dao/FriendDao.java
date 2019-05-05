package chat.dao;

import java.util.List;

import chat.entity.Friend;
import chat.entity.Personal;

public interface FriendDao {
	//查找好友
	public Personal search(String search_name);
	//添加好友
	public void addFriend(Friend f);
	//加载好友
	public List<Friend> loadFriend(String personal_id);
	
	
}
