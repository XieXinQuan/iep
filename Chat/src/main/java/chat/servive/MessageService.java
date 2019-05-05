package chat.servive;

import chat.entity.Message;
import chat.util.ChatResult;

public interface MessageService {
	public ChatResult<Message> sendMessage(String personal_id,String message);
}
