package QuanDiary.entity;

public class SecondMenu {
	private Long id;
	private Long menu;
	private String title;
	private String path;
	private Integer type;
	private Integer status;
	private String url_path;
	public String getUrl_path() {
		return url_path;
	}
	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}

	@Override
	public String toString() {
		return "SecondMenu [id=" + id + ", menu=" + menu + ", title=" + title
				+ ", path=" + path + ", type=" + type + ", status=" + status
				+ ", url_path=" + url_path + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMenu() {
		return menu;
	}
	public void setMenu(Long menu) {
		this.menu = menu;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
