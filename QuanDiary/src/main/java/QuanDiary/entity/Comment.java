package QuanDiary.entity;

import java.sql.Timestamp;

public class Comment {
	private Long id;
	private Long user;
	private Long log;
	private Long reply_user;
	private Integer status;
	private String content;
	private Timestamp create_time;
	@Override
	public String toString() {
		return "Comment [id=" + id + ", user=" + user + ", log=" + log
				+ ", reply_user=" + reply_user + ", status=" + status
				+ ", content=" + content + ", create_time=" + create_time + "]";
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
	public Long getReply_user() {
		return reply_user;
	}
	public void setReply_user(Long reply_user) {
		this.reply_user = reply_user;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
	
}
