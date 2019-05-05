package QuanDiary.entity;

import java.sql.Timestamp;

public class Dept {
	private Long id;
	private Long parent;
	private Long company;
	private Long user;
	private String name;
	private Integer status;
	private Timestamp create_time;
	private Integer order_no;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
	public Integer getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Integer order_no) {
		this.order_no = order_no;
	}
	
	@Override
	public String toString() {
		return "Dept [id=" + id + ", parent=" + parent + ", company=" + company
				+ ", user=" + user + ", name=" + name + ", status=" + status
				+ ", create_time=" + create_time + ", order_no=" + order_no
				+ "]";
	}
	
}
