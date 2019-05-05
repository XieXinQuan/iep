package QuanDiary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import QuanDiary.entity.Company;
import QuanDiary.entity.User;

public interface UserDao {
	public Integer checkName(String loginName);
	public void save(User user);
	public User login(String loginName);
	public Integer userType(Long id);
	public User loadUserById(Long userId);
	public Company loadCompanyByCompany(Long companyId);
	//未分配部门
	public List<User> loadUserByCompanyButDeptNull(Long companyId);
	//部门人员但不包含子部门
	public List<User> loadUserByDept(Long dept);
	//加载部门人员，不包含子部门--翻页
	public List<User> loadDeptUser(Long dept, Integer pageIndex, Integer pageCount);
	//加载部门人员，不包含子部门--人员总数
	public Integer loadDeptUserCount(Long dept);
	public Long userCompany(Long userId);
	public List<Map<String, Object>> testMap();
	public List<Map<String, Object>> testParameters(@Param("loginName") String loginName, 
			@Param("status") Integer status);
	public List<Map<String, Object>> testParameters1(String loginName, Integer status);
	public String loadName(Long userId);
	public void addEmployeeBatch(List<User> user);
	//仅用于员工管理公司
	public List<HashMap<String, Object>> loadCompanyUser(Long companyId, Integer pageIndex, Integer pageCount);
	//统计公司人数
	public Integer loadCompanyUserCount(Long companyId);
	
	public List<HashMap<String, Object>> loadCompanyUserNoDept(Long companyId, Integer pageIndex, Integer pageCount);
	public Integer loadCompanyUserCountNoDept(Long companyId);
	public void updateUserStatus(User user);
	
	public List<User> loadAllAskOfLeaveUser(@Param("list")List<Integer> list);
}
