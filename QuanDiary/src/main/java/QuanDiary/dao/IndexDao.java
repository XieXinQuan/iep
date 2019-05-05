package QuanDiary.dao;

import java.util.List;

import QuanDiary.entity.Menu;
import QuanDiary.entity.SecondMenu;

public interface IndexDao {
	public List<Menu> loadMenu(Integer type);
	public List<SecondMenu> loadSecondMenu(Long menu, Integer type);
	public Long findMenu(String title);
}
