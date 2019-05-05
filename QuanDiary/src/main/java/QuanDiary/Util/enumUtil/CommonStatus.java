package QuanDiary.Util.enumUtil;

public enum CommonStatus {
	Defeated(0, "失败"),
	Normal(1, "正常"),
	GoToLogin(88, "重新登录"),
	Disabled(91, "停用"),
	Delete(92, "删除");
	private final int key;
	private final String value;
	private CommonStatus(int key, String value) {
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
