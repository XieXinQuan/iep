package QuanDiary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import QuanDiary.entity.Comment;
import QuanDiary.entity.Log;
import QuanDiary.entity.LogLike;
import QuanDiary.entity.LogRead;
import QuanDiary.entity.LogTemplate;

public interface LogLikeDao {
	public void saveLogLike(LogLike like);
	public void saveLogRead(LogRead logRead);
	public Integer checkLogLike(Long log, Long user);
	public Integer checkLogRead(Long log, Long user);


	public Comment loadCommentById(Long id);
	public List<HashMap<String, Object>> loadLogLike(Long logId);
	public List<HashMap<String, Object>> loadLogRead(Long logId);
	
	public void saveLogComment(Comment comment);
	public List<HashMap<String, Object>> loadLogComment(Long logId);
	
	public List<HashMap<String, Object>> logStatistical(@Param("createTime") String createTime, 
			@Param("companyId") Long companyId, @Param("page") Integer page,
			@Param("rows") Integer rows);
	public void updateCommentStatus(Comment comment);
}
