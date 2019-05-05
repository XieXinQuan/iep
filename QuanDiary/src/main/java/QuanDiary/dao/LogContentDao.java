package QuanDiary.dao;

import java.util.List;
import java.util.Map;

import QuanDiary.entity.LogContent;

public interface LogContentDao {
	public void save(List<Map<String, Object>> content);
	public List<LogContent> viewDiary(Long log);
}
