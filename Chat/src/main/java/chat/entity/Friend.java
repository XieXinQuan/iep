package chat.entity;

public class Friend {
	private String personal_id;
	private String friend_id;
	private String friend_name;
	private String friend_group;
	@Override
	public String toString() {
		return "friend [friend_id=" + friend_id + ", personal_id="
				+ personal_id + ", friend_name=" + friend_name
				+ ", friend_group=" + friend_group + "]";
	}
	public String getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
	public String getPersonal_id() {
		return personal_id;
	}
	public void setPersonal_id(String personal_id) {
		this.personal_id = personal_id;
	}
	public String getFriend_name() {
		return friend_name;
	}
	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}
	public String getFriend_group() {
		return friend_group;
	}
	public void setFriend_group(String friend_group) {
		this.friend_group = friend_group;
	}
	
}
