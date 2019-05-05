package chat.dao;

import chat.entity.Message;

public interface MessageDao {
	//保存信息
	public void sendMessage(Message m);
}
