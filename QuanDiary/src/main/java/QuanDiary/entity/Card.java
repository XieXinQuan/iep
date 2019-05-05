package QuanDiary.entity;

import java.sql.Timestamp;

public class Card {
	private Long id;
	private Long company;
	private Long user;
	private Integer status;
	private Timestamp create_time;
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
	@Override
	public String toString() {
		return "Card [id=" + id + ", company=" + company + ", user=" + user
				+ ", status=" + status + ", create_time=" + create_time + "]";
	}
	
}
