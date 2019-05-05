package QuanDiary.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import QuanDiary.dao.IndexDao;
import QuanDiary.entity.Menu;
import QuanDiary.entity.SecondMenu;

@Service("indexService")
public class IndexService {
	@Resource
	IndexDao indexDao;
	public List<Menu> loadMenu(Integer type){
		List<Menu> menus = indexDao.loadMenu(type);
		if (menus.size() > 0){
			return menus;
		}
		
		return null;
	}
	public Long findMenu(String title){
		return indexDao.findMenu(title);
	}
	public List<SecondMenu> loadSecondMenu(Long menu, Integer type){
		List<SecondMenu> secondMenus = indexDao.loadSecondMenu(menu, type);
		if (secondMenus.size() > 0){
			return secondMenus;
		}
		return null;
	}
}
