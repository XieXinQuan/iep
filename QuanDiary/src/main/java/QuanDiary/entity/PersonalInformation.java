package QuanDiary.entity;

public class PersonalInformation {
	private Long id;
	private Long user;
	private Long company;
	private Integer status;
	private Integer user_type;
	private String sex;
	private String phone;
	private String adress;
	private String city;
	private String democratic;
	private Integer age;
	private Double height;
	private Double weight;
	private String identity_card;
	private String hobby;
	private String introduce;
	private String email;
	@Override
	public String toString() {
		return "PersonalInformation [id=" + id + ", user=" + user
				+ ", company=" + company + ", status=" + status
				+ ", user_type=" + user_type + ", sex=" + sex + ", phone="
				+ phone + ", adress=" + adress + ", city=" + city
				+ ", democratic=" + democratic + ", age=" + age + ", height="
				+ height + ", weight=" + weight + ", identity_card="
				+ identity_card + ", hobby=" + hobby + ", introduce="
				+ introduce + ", email=" + email + "]";
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
	public Long getCompany() {
		return company;
	}
	public void setCompany(Long company) {
		this.company = company;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUser_type() {
		return user_type;
	}
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDemocratic() {
		return democratic;
	}
	public void setDemocratic(String democratic) {
		this.democratic = democratic;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getIdentity_card() {
		return identity_card;
	}
	public void setIdentity_card(String identity_card) {
		this.identity_card = identity_card;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
