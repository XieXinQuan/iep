package QuanDiary.Util.toolUtil;

public class TranscodingUtil {
	public static String TranCode(String ISOString){
		try{
			byte[] source = ISOString.getBytes("ISO8859-1");
			return new String(source, "UTF-8");
		}catch(Exception e){
			System.out.println(".....编码转换异常！");
		}
		return "";

	}
}
