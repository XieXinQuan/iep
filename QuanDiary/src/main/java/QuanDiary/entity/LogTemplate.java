package QuanDiary.entity;

public class LogTemplate {
	private Long id;
	private Long company;
	private String title;
	private Integer is_common;
	private Integer status;
	@Override
	public String toString() {
		return "LogTemplate [id=" + id + ", comany=" + company + ", title="
				+ title + ", is_common=" + is_common + ", status=" + status
				+ "]";
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getIs_common() {
		return is_common;
	}
	public void setIs_common(Integer is_common) {
		this.is_common = is_common;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
