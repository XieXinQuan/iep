package QuanDiary.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.dao.LogTemplateDao;
import QuanDiary.entity.LogTemplate;
@Service("logTemplateService")
public class LogTemplateService {
	@Resource
	LogTemplateDao logTemplateDao;
	public Long save(Long company, String title){
		LogTemplate logTemplate = new LogTemplate();
		logTemplate.setCompany(company);
		logTemplate.setStatus(CommonStatus.Normal.getValue());
		logTemplate.setTitle(title);
		logTemplateDao.save(logTemplate);
		return logTemplate.getId();
	}
	public List<LogTemplate> loadModule(Long company, Integer status, Integer status1
			,Integer pageIndex, Integer pageCount){
		return logTemplateDao.loadModule(company, status, status1,  pageIndex,  pageCount);
	}
	public Integer countByLoadModule(Long company, Integer status, Integer status1){
		return logTemplateDao.countByLoadModule(company, status, status1);
	}
	public List<LogTemplate> loadModuleByCompany(Long company, Integer status){
		return logTemplateDao.loadModuleByCompany(company, status);
	}
	public List<LogTemplate> loadModuleById(Long company, Integer status){
		return logTemplateDao.loadModuleById(company, status);
	}
}
