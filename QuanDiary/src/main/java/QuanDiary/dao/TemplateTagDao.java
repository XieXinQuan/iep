package QuanDiary.dao;

import java.util.List;

import QuanDiary.entity.TemplateTag;

public interface TemplateTagDao {
	public void saveByBatch(List<TemplateTag> templateTags);
	public List<TemplateTag> loadModuleById(Long id);
}
