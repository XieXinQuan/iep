package QuanDiary.entity;

import java.sql.Timestamp;

public class Log {
	private Long id;
	private Long user;
	private Long template;
	private Long company;
	private String title;
	private String memo;
	private Integer status;
	private Integer read_limits;
	private Timestamp create_time;
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
	public Long getCompany() {
		return company;
	}
	public void setCompany(Long company) {
		this.company = company;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getStatus() {
		return status;
	}
	public Long getTemplate() {
		return template;
	}
	public void setTemplate(Long template) {
		this.template = template;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRead_limits() {
		return read_limits;
	}
	public void setRead_limits(Integer read_limits) {
		this.read_limits = read_limits;
	}
	
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "Log [id=" + id + ", user=" + user + ", template=" + template
				+ ", company=" + company + ", title=" + title + ", memo="
				+ memo + ", status=" + status + ", read_limits=" + read_limits
				+ ", create_time=" + create_time + "]";
	}
	
}
