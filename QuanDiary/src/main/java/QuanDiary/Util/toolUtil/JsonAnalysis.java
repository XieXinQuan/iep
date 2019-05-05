package QuanDiary.Util.toolUtil;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class JsonAnalysis {
	public static List<JSONObject> analysis(String content){
		List<JSONObject> result = new ArrayList<JSONObject>();
		content = content.substring(1, content.length()-1);
		String [] jsonData = content.split("},");
		for (int i = 0;i<jsonData.length;i++){
			if(i!=jsonData.length-1){
				jsonData[i] = jsonData[i]+"}";
			}
			JSONObject jsonObject = JSONObject.fromObject(jsonData[i]);
			result.add(jsonObject);
		}
		return result;
		
	}
}
