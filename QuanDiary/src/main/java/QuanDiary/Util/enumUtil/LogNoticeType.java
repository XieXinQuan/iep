package QuanDiary.Util.enumUtil;

public enum LogNoticeType {
	Company(0, "公司全体人员"),
	Dept(1, "部门人员"),
	User(2, "指定个人");
	private final int key;
	private final String value;
	private LogNoticeType(int key, String value) {
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
