package QuanDiary.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import QuanDiary.entity.User;

public interface UserService {
	public Boolean checkName(String loginName);
	public Boolean save(String loginName, String password, String displayName);
	public HashMap<String, String> login(String loginName, String password);
	public Integer userType(Long id);
	public Long userCompany(Long userId);
	public List<Map<String, Object>> testMap();
	public List<Map<String, Object>> testParameters(String loginName, Integer status);
	public List<Map<String, Object>> testParameters1(String loginName, Integer status);
	public String loadName(Long userId);
	public void addEmployeeBatch(List<Map<String, Object>> users);
}
