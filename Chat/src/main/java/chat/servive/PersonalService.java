package chat.servive;

import chat.entity.Personal;
import chat.util.ChatResult;

public interface PersonalService {
	public ChatResult<Object> addPersonal(String name,String password);
	public ChatResult<Personal> login(String name,String password);
	public ChatResult<Personal> name(String personal_id);
}
