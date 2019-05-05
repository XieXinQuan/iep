package QuanDiary.entity;

import java.sql.Timestamp;

public class LogLike {
	private Long id;
	private Long user;
	private Long log;
	private Integer status;
	private Timestamp create_time;
	@Override
	public String toString() {
		return "LogLike [id=" + id + ", user=" + user + ", log=" + log
				+ ", status=" + status + ", create_time=" + create_time + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public Long getLog() {
		return log;
	}
	public void setLog(Long log) {
		this.log = log;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
}
