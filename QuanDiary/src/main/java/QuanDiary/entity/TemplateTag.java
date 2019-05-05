package QuanDiary.entity;

import java.sql.Timestamp;

public class TemplateTag {
	private Long id;
	private Long template;
	private String title;
	private Integer type;
	private Integer status;
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private Integer order_no;
	private Timestamp creatr_time;
	@Override
	public String toString() {
		return "TemplateTag [id=" + id + ", template=" + template + ", title="
				+ title + ", type=" + type + ", status=" + status
				+ ", content=" + content + ", order_no=" + order_no
				+ ", creatr_time=" + creatr_time + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTemplate() {
		return template;
	}
	public void setTemplate(Long template) {
		this.template = template;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Integer order_no) {
		this.order_no = order_no;
	}
	public Timestamp getCreatr_time() {
		return creatr_time;
	}
	public void setCreatr_time(Timestamp creatr_time) {
		this.creatr_time = creatr_time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
