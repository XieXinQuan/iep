package QuanDiary.Util.toolUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class timeUtil {
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	/**
	* 得到本周周一
	* 
	* @return yyyy-MM-dd
	*/
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		System.out.println(c.getTime());
		return format.format(c.getTime());
	}

	/**
	* 得到本周周日
	* 
	* @return yyyy-MM-dd
	*/
	public static String getSundayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		return format.format(c.getTime());
	}
}
