package QuanDiary.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import QuanDiary.dao.TemplateTagDao;
import QuanDiary.entity.TemplateTag;

@Service("templateTagService")
public class TemplateTagService {
	@Resource
	TemplateTagDao templateTagDao;
	
	public void saveByBatch(List<TemplateTag> templateTags){
		templateTagDao.saveByBatch(templateTags);
	}
	public List<TemplateTag> loadModuleById(Long id){
		return templateTagDao.loadModuleById(id);
	}
}
