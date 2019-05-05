package chat.entity;

public class Message {
	private String personal_id;
	private String friend_id;
	private String message;
	private	Long message_time;
	@Override
	public String toString() {
		return "Message [personal_id=" + personal_id + ", friend_id="
				+ friend_id + ", message=" + message + ", message_time="
				+ message_time + "]";
	}
	public String getPersonal_id() {
		return personal_id;
	}
	public void setPersonal_id(String personal_id) {
		this.personal_id = personal_id;
	}
	public String getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getMessage_time() {
		return message_time;
	}
	public void setMessage_time(Long message_time) {
		this.message_time = message_time;
	}
	
	
}
