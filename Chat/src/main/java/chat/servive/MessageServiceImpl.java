package chat.servive;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import chat.dao.MessageDao;
import chat.entity.Message;
import chat.util.ChatResult;
@Service("messageService")
public class MessageServiceImpl implements MessageService{

	@Resource
	MessageDao dao;
	public ChatResult<Message> sendMessage(String personal_id,String message) {
		ChatResult<Message> result = new ChatResult<Message>();
		String[] msg = message.split("%");
		String [] a = null;
		Message m = new Message();
		for(int i=0;i<msg.length;i++){
				if(msg[i]!=null){
				a = msg[i].split("|");
				m.setPersonal_id(personal_id);
				m.setFriend_id(a[0]);
				m.setMessage(a[2]);
				Long b= Long.parseLong(a[3]);
				m.setMessage_time(b);
				dao.sendMessage(m);
			}
		}
		result.setMsg("保存成功！");
		result.setStatus(0);
		return result;
	}
	
}
