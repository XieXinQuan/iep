package QuanDiary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import QuanDiary.entity.Log;
import QuanDiary.entity.LogNotice;
import QuanDiary.entity.LogTemplate;

public interface LogDao {

	public Integer todayIsFinishLog(Long userId, String time);
	public void save(Log log);
	public List<Log> loadMyLog(Long user, Integer pageIndex, Integer pageCount);
	public Integer countByLoadMyLog(Long user, Integer status);
	public Log loadModuleById(Long id);
	public void delDiary(Log log);
	// 公司
	public List<HashMap<String, Object>> loadOtherUserLog(@Param("id") Long id,@Param("page") Integer page,@Param("rows") Integer rows);
	public Integer countByLoadOtherUserLog(Long id);
	//其他员工日志
	public List<HashMap<String, Object>> loadOtherUserLogIsCompany(@Param("company") Long company, @Param("currUser") Long currUser, 
			@Param("targetUser") Long targetUser, @Param("dept") Long dept, @Param("isNoDept") Boolean isNoDept,@Param("time") String time,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("page") Integer page,@Param("rows") Integer rows);
	public Integer countByLoadOtherUserLogIsCompany(@Param("company") Long company, @Param("currUser") Long currUser, 
			@Param("targetUser") Long targetUser, @Param("dept") Long dept, @Param("isNoDept") Boolean isNoDept, @Param("time") String time,
			@Param("startTime") String startTime, @Param("endTime") String endTime);
	//导出其他员工日志
	public List<HashMap<String, Object>> loadOtherUserLogIsCompanyByExportExcel(@Param("company") Long company, 
			@Param("currUser") Long currUser, @Param("targetUser") Long targetUser, 
			@Param("dept") Long dept, @Param("isNoDept") Boolean isNoDept,@Param("time") String time,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	//超级管理员
	public List<HashMap<String, Object>> logListDataByAdmin(@Param("company") Long company, @Param("time") String time,
			@Param("page") Integer page, @Param("rows")Integer rows);
	public Integer countLogListDataByAdmin(@Param("company") Long company, @Param("time") String time);
	
	public List<HashMap<String, Object>> logWritingSituation(@Param("time") String time, @Param("type") Integer type, 
			@Param("companyId") Long companyId, @Param("page") Integer page,@Param("rows") Integer rows);
	
	public void updateLogStatus(Log log);
	
	
	public void saveLogNotice(LogNotice logNotice);
	public void delLogNotice(LogNotice logNotice);
	public LogNotice loadLogNoticeById(Long id);
	public List<HashMap<String, Object>> loadLogNotice(@Param("company") Long company,
			@Param("dept") Long dept, @Param("user") Long user,
			@Param("page") Integer page, @Param("rows") Integer rows);
	
	public Integer loadLogNoticeCount(@Param("company") Long company, @Param("dept") Long dept, @Param("user") Long user);

}
