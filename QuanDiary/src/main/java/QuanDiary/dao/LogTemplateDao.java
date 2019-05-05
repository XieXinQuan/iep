package QuanDiary.dao;

import java.util.List;

import QuanDiary.entity.LogTemplate;

public interface LogTemplateDao {
	public void save(LogTemplate logTemplate);
	public List<LogTemplate> loadModule(Long company, Integer status,  Integer status1,
			Integer pageIndex, Integer pageCount);
	public Integer countByLoadModule(Long company, Integer status, Integer status1);
	public List<LogTemplate> loadModuleByCompany(Long company, Integer status);
	public List<LogTemplate> loadModuleById(Long company, Integer status);
	public void delLogModule(LogTemplate logTemplate);
	public LogTemplate loadLogTemplateById(Long id);
}
