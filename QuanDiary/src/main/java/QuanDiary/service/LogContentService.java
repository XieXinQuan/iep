package QuanDiary.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import QuanDiary.dao.LogContentDao;

@Service("logContentService")
public class LogContentService {
	@Resource
	LogContentDao logContentDao;
	public void save(List<Map<String, Object>> content){
		logContentDao.save(content);
	}
}
