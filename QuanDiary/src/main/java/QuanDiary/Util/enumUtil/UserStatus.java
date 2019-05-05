package QuanDiary.Util.enumUtil;

public enum UserStatus {
	OnJob(1, "在职"),
	Departure(11, "离职"),
	Abnormal(12, "账号异常"),
	AskForLeave(13, "请假"),
	BusinessTrip(14, "出差"),
	Card(15, "每日打卡"),
	NoDeal(16, "未被处理"),
	UnDo(17, "撤销"),
	Refused(18, "拒绝"),
	Agree(19, "同意"),
	Disabled(91, "停用"),
	Delete(92, "删除");
	private final int key;
	private final String value;
	private UserStatus(int key, String value) {
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
