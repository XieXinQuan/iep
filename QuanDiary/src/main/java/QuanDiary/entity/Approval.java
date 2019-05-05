package QuanDiary.entity;

import java.sql.Timestamp;

public class Approval {
	private Long id;
	private Long company;
	private Long user;
	private Integer status;
	private Timestamp create_time;
	private Timestamp start_time;
	private Timestamp end_time;
	private String reason;
	private Integer type;
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
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Approval [id=" + id + ", company=" + company + ", user=" + user
				+ ", status=" + status + ", create_time=" + create_time
				+ ", start_time=" + start_time + ", end_time=" + end_time
				+ ", reason=" + reason + ", type=" + type + "]";
	}
	
}
