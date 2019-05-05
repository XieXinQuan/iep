package QuanDiary.Util.enumUtil;

public enum UserType {

	Common(1, "普通用户"),
	CompanyCommon(2, "公司普通"),
	CompanyManager(3, "公司管理员"),
	CompanyDeptManager(5, "部门管理员"),
	Admin(4, "系统管理员");
	private final int key;
	private final String value;
	private UserType(int key, String value) {
		this.key = key;
		this.value = value;
	}
	public int getValue(){
		return key;
	}
	public String getExplain(){
		return value;
	}
}
