package QuanDiary.Util.enumUtil;

public enum LogStatus {
	Normal(1, "正常"),
	UnDo(22, "撤销"),
	Refused(23, "拒绝"),
	Disabled(91, "停用"),
	Delete(92, "删除");
	private final int key;
	private final String value;
	private LogStatus(int key, String value) {
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
