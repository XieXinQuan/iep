package QuanDiary.dao;

import java.util.List;

import QuanDiary.entity.Company;
import QuanDiary.entity.Dept;

public interface DeptDao {
	public List<Dept> loadDept(Long company, Integer status);
	public void save(Dept dept);
	public Integer checkNameByCompany(Long company, Integer status, String name);
	public Dept loadDeptById(Long id);
	
	public void saveCompany(Company company);
	public List<Company> loadAllCompany();
}
