package chat.util;
/*
 * UUID:��һ�������ַ����㷨,�������������ݿ�����
 * Ϊʲô??	�������������ַ�ʽ:����Ӧ�÷������������ �������ݿ����������
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;


public class ChatUtil {
	
	/*
	 * ����UUID�㷨��������
	 */
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id.replace("-", "");
	}
	
	public static String md5(String src) throws NoSuchAlgorithmException{
		
		//����md5��msg����
		MessageDigest md = 
			MessageDigest.getInstance("MD5");
		byte[] input = src.getBytes();
		byte[] output = md.digest(input);//���ֽ���Ϣ����
		//��md5�����output���ת���ַ���
		String result = Base64.encodeBase64String(output);
		return result;
		
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(createId());
		System.out.println(md5(createId()));
	}
}
