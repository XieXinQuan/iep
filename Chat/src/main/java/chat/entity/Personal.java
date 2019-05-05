package chat.entity;

public class Personal {
	private String personal_id;
	private String personal_name;
	private String personal_password;
	@Override
	public String toString() {
		return "personal [personal_id=" + personal_id + ", personal_name="
				+ personal_name + ", personal_password=" + personal_password
				+ "]";
	}
	public String getPersonal_id() {
		return personal_id;
	}
	public void setPersonal_id(String personal_id) {
		this.personal_id = personal_id;
	}
	public String getPersonal_name() {
		return personal_name;
	}
	public void setPersonal_name(String personal_name) {
		this.personal_name = personal_name;
	}
	public String getPersonal_password() {
		return personal_password;
	}
	public void setPersonal_password(String personal_password) {
		this.personal_password = personal_password;
	}
	
}
