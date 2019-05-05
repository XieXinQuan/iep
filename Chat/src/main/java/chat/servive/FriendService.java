package chat.servive;

import java.util.List;

import chat.entity.Friend;
import chat.entity.Personal;
import chat.util.ChatResult;

public interface FriendService {
	//查找
	public ChatResult<Personal> search(String personal_id,String search_name);
	public ChatResult<Friend> addFriend(String personal_id,String friend_id);
	public ChatResult<List<Friend>> loadFriend1(String personal_id);
	
}
