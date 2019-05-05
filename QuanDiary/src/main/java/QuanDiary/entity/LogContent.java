package QuanDiary.entity;

public class LogContent {
	private Long id;
	private Long company;
	private Long user;
	private Long log;
	private Long tag;
	private String content;
	@Override
	public String toString() {
		return "LogContent [id=" + id + ", company=" + company + ", user="
				+ user + ", log=" + log + ", tag=" + tag + ", content="
				+ content + "]";
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
	public Long getTag() {
		return tag;
	}
	public void setTag(Long tag) {
		this.tag = tag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
