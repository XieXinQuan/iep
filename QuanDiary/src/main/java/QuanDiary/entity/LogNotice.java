package QuanDiary.entity;

import java.sql.Timestamp;

public class LogNotice {
	private Long id;
	private Long company;
	private Long dept;
	private Long user;
	private Integer status;
	private Timestamp create_time;
	private String content;
	private String title;
	private Integer type;
	@Override
	public String toString() {
		return "LogNotice [id=" + id + ", company=" + company + ", dept="
				+ dept + ", user=" + user + ", status=" + status
				+ ", create_time=" + create_time + ", content=" + content
				+ ", title=" + title + ", type=" + type + "]";
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
	public Long getDept() {
		return dept;
	}
	public void setDept(Long dept) {
		this.dept = dept;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
