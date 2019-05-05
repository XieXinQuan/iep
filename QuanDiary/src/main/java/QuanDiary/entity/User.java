package QuanDiary.entity;

import java.sql.Timestamp;

public class User {

	private Long id;
	private Long company;
	private Long dept;
	private String login_name;
	private String display_name;
	private String password;
	private Integer status;
	private Integer user_type;
	private Timestamp lock_end_time;
	@Override
	public String toString() {
		return "User [id=" + id + ", company=" + company + ", dept=" + dept
				+ ", login_name=" + login_name + ", display_name="
				+ display_name + ", password=" + password + ", status="
				+ status + ", user_type=" + user_type + ", lock_end_time="
				+ lock_end_time + "]";
	}
	public Long getDept() {
		return dept;
	}
	public void setDept(Long dept) {
		this.dept = dept;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompany() {
		return company;
	}
	public void setCompany(Long company) {
		this.company = company;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUser_type() {
		return user_type;
	}
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}
	public Timestamp getLock_end_time() {
		return lock_end_time;
	}
	public void setLock_end_time(Timestamp lock_end_time) {
		this.lock_end_time = lock_end_time;
	}
	
}
