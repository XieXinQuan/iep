package QuanDiary.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.Util.enumUtil.UserStatus;
import QuanDiary.Util.enumUtil.UserType;
import QuanDiary.Util.md5Util.Md5;
import QuanDiary.dao.ApprovalDao;
import QuanDiary.dao.DeptDao;
import QuanDiary.dao.PersonalInformationDao;
import QuanDiary.dao.UserDao;
import QuanDiary.entity.Approval;
import QuanDiary.entity.Card;
import QuanDiary.entity.Company;
import QuanDiary.entity.Dept;
import QuanDiary.entity.PersonalInformation;
import QuanDiary.entity.User;
import QuanDiary.service.DeptService;

@Controller
@RequestMapping("/company")
public class companyController {
	@Resource
	DeptDao deptDao;
	@Resource
	UserDao userDao;
	@Resource
	ApprovalDao approvalDao;
	@Resource
	DeptService deptService;
	@Resource 
	PersonalInformationDao pInformationDao;
	
	@RequestMapping("/saveDept.do")
	@ResponseBody
	public HashMap<String, Object> saveDept(String deptName,Long deptId,
			HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(deptId == -1){
			result.put("status", 2);
			result.put("msg", "该部门不能拥有子部门！");
			return result;
		}

		//deptName = TranscodingUtil.TranCode(deptName);
		if(request.getSession().getAttribute("userId") != null){
			
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long company = userDao.userCompany(userId);
			if(deptName != null && !"".equals(deptName)){
				

				Integer isHaveDeptName = deptDao.checkNameByCompany(company, CommonStatus.Normal.getValue(), deptName);

				if(isHaveDeptName == 0){
					Dept dept = new Dept();
					dept.setCompany(company);
					dept.setUser(userId);
					dept.setCreate_time(new Timestamp(System.currentTimeMillis()));
					dept.setName(deptName);
					dept.setStatus(CommonStatus.Normal.getValue());
					if(deptId != null && deptId != 0) dept.setParent(-deptId);
					deptDao.save(dept);
					result.put("status", CommonStatus.Normal.getValue());
					result.put("msg", "保存成功！");
				}else {
					result.put("status", 2);
					result.put("msg", "已有该部门！");
				}
			}
		}else {
			result.put("status", CommonStatus.GoToLogin.getValue());
			result.put("msg", "请重新登录！");
		}
		return result;
	}
	@RequestMapping("/loadDeptData.do")
	@ResponseBody
	public void loadDeptData(String deptName,
			HttpServletRequest request, HttpServletResponse response){

		JSONArray jsonArray = new JSONArray();
		//查找所有部门
		if(request.getSession().getAttribute("userId") != null){
			
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			if(userDao.userType(userId) == 4){
				
				
				JSONObject root = new JSONObject();
				root.put("id", 0);
				root.put("pId", 0);
				root.put("name", "日志管理系统");
				root.put("open", true);
				jsonArray.add(root);
				List<Company> companys = deptDao.loadAllCompany();
				for(Company company : companys){
					JSONObject comp = new JSONObject();
					comp.put("id", company.getId());
					comp.put("pId", 0);
					comp.put("name", company.getShort_name()==null?company.getName():company.getShort_name());
					jsonArray.add(comp);
				}
			}else{
				Long company = userDao.userCompany(userId);
				Company comp = userDao.loadCompanyByCompany(company);
				List<Dept> depts = deptDao.loadDept(company, CommonStatus.Normal.getValue());
				JSONObject root = new JSONObject();
				root.put("id", 0);
				root.put("pId", 0);
				root.put("name", comp.getShort_name()==null?comp.getName():comp.getShort_name());
				root.put("open", true);
				jsonArray.add(root);
				
				for(Dept dept : depts){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", -dept.getId());
					jsonObject.put("pId", dept.getParent() == null?0:-dept.getParent());
					jsonObject.put("name", dept.getName());
	
					jsonArray.add(jsonObject);
	
				}
			}

	        try{
	        	response.getWriter().print(jsonArray.toString());
	        }catch(Exception e){
	        	System.out.println("....加载部门数据异常");
	        }
		}
	}
	public JSONObject putData(Object id,Object pId,Object name,Object t,Boolean isShowOpen, Object open){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("pId", pId);
		jsonObject.put("name", name);
		jsonObject.put("t", t);
		if(isShowOpen){
			jsonObject.put("open", open);
		}
		return jsonObject;
	}
	@RequestMapping(value="/loadDeptData1.do")
	@ResponseBody
	public void loadDeptData1(String deptName,
			HttpServletRequest request, HttpServletResponse response){

		JSONArray jsonArray = new JSONArray();
		//查找所有部门
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			if(userDao.userType(userId) == 4){
				
				
				JSONObject root = new JSONObject();
				root.put("id", 0);
				root.put("pId", 0);
				root.put("name", "日志管理系统");
				root.put("open", true);
				jsonArray.add(root);
				List<Company> companys = deptDao.loadAllCompany();
				for(Company company : companys){
					JSONObject comp = new JSONObject();
					comp.put("id", company.getId());
					comp.put("pId", 0);
					comp.put("name", company.getShort_name()==null?company.getName():company.getShort_name());
					jsonArray.add(comp);
				}
			}else{
				Long company = userDao.userCompany(userId);
				Company comp = userDao.loadCompanyByCompany(company);
				List<Dept> depts = deptDao.loadDept(company, CommonStatus.Normal.getValue());
				JSONObject root = new JSONObject();
				root.put("id", 0);
				root.put("pId", 0);
				root.put("name", comp.getShort_name()==null?comp.getName():comp.getShort_name());
				root.put("open", true);
				jsonArray.add(root);
				
				for(Dept dept : depts){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", -dept.getId());
					jsonObject.put("pId", dept.getParent() == null?0:-dept.getParent());
					jsonObject.put("name", dept.getName());
	
	
	
					jsonArray.add(jsonObject);
					List<User> users = userDao.loadUserByDept(dept.getId());
					for(User user : users){
						JSONObject j = new JSONObject();
						j.put("id", user.getId());
						j.put("pId", -dept.getId());
						j.put("name", user.getDisplay_name()==null?user.getLogin_name():user.getDisplay_name());
	
						jsonArray.add(j);
					}
				}
				
				
	
				
				//未分配部门
				JSONObject noDept = new JSONObject();
				noDept.put("id", -1);
				noDept.put("name", "未分配部门");
				noDept.put("pId", 0);
				List<User> users = userDao.loadUserByCompanyButDeptNull(company);
	
				for(User user : users){
					JSONObject userObj = new JSONObject();
					userObj.put("id", user.getId());
					userObj.put("pId", -1);
					userObj.put("name", user.getDisplay_name() == null? user.getLogin_name() : user.getDisplay_name());
					jsonArray.add(userObj);
				}
				jsonArray.add(noDept);

			}
			
			
			//System.out.println(".......data:"+jsonArray.toString());
			//JSONArray arr = JSONArray.fromObject(jsonArray);
	        response.setCharacterEncoding("UTF-8");
	        try{
	        	response.getWriter().print(jsonArray.toString());
	        }catch(Exception e){
	        	System.out.println("....加载部门数据异常");
	        }
		}
	}
	/*
	 * id:如为0 则为公司 如为负值 则转正后为deptId 如为正值，则为userId
	 */
	@RequestMapping("/employeeInformation.do")
	@ResponseBody
	public void employeeInformation(Long id,
			HttpServletRequest request, HttpServletResponse response){
		try{
			request.setAttribute("id", id);
			request.getRequestDispatcher("/jsp/user/employeeInformation.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/myWork.do")
	@ResponseBody
	public void myWork(HttpServletRequest request, HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/user/myWork.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/loadApproval.do")
	@ResponseBody
	public void loadApproval(HttpServletRequest request, HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/user/approvalList.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/loadApprovalData.do")
	@ResponseBody
	public void loadApprovalData(String time, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		if(time == null) time = sdf.format(new Date());
		Integer pageIndex = (page-1) * rows;
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long companyId = userDao.userCompany(userId);
			List<HashMap<String, Object>> list = approvalDao.loadApproval(companyId, pageIndex, rows);
			jsonObject.put("rows", list);
			
			//records当前页展示的数量
			Integer total = userDao.loadCompanyUserCount(companyId);
			jsonObject.put("records", total);
			//total 总页数
			total = total%rows==0? total/rows: total/rows+1;
			jsonObject.put("total", total);
			try{
				response.getWriter().write(jsonObject.toJSONString());
			}catch(Exception e){
				System.out.println("............log/loadLogModuleData.do异常！");
			}
		}
		
	}
	/**
	 * 
	 * @param type 1:请假 2：出差 3离职
	 * @param startTime 
	 * @param endTime 离职没有此选项！！
	 * @param content
	 * @return
	 */
	@RequestMapping("/saveAskForLeave.do")
	@ResponseBody
	public HashMap<String, Object> saveAskForLeave(Integer type,
			Long startTime, Long endTime, String content,
			HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long companyId = userDao.userCompany(userId);
			if(type == 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				if(approvalDao.checkUserCard(userId, sdf.format(new Date())) == 0){
					Card card = new Card();
					card.setCompany(companyId);
					card.setCreate_time(new Timestamp(System.currentTimeMillis()));
					card.setUser(userId);
					card.setStatus(CommonStatus.Normal.getValue());
					approvalDao.saveCard(card);
					result.put("msg", "恭喜签到成功！");
				}else{
					result.put("msg", "您今天已经签到过了！");
				}
			}else{
				
				Approval approval = new Approval();
				approval.setCompany(companyId);
				approval.setUser(userId);
				if(type != 3) approval.setEnd_time(new Timestamp(endTime));
				Integer status = UserStatus.NoDeal.getValue();
				approval.setStatus(status);
				approval.setReason(content);
				approval.setStart_time(new Timestamp(startTime));
				
				if(type == 1) type = UserStatus.AskForLeave.getValue();
				if(type == 2) type = UserStatus.BusinessTrip.getValue();
				if(type == 3) type = UserStatus.Departure.getValue();
				approval.setType(type);
				approval.setCreate_time(new Timestamp(System.currentTimeMillis()));
				
				approvalDao.saveApproval(approval);
				
				result.put("msg", "申请已提交，请等待管理员审核！");
			}

			
			
		}
		
		return result;
	}
	/**
	 * 
	 * @param id >0：userId ==0公司，-1：未分配部门 <-1 deptId
	 * @return
	 */
	@RequestMapping("/loadUserInfor.do")
	@ResponseBody
	public void loadUserInfor(Long id, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response){
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		jsonObject.put("page", page);
		if(page == null) page = 0;
		if(rows == null) rows = 20;
		Integer pageIndex = (page-1) * rows;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		Integer total = 0;
		if(id != null && id < -1){

			List<User> users = userDao.loadDeptUser(-id, pageIndex, rows);
			Dept dept = deptDao.loadDeptById(-id);
			for(User user : users){
				HashMap<String, Object> h = new HashMap<String, Object>();
				h.put("id", user.getId());
				h.put("displayName", user.getDisplay_name() == null?user.getLogin_name():user.getDisplay_name());
				h.put("deptName", dept.getName());
				h.put("status", user.getStatus());
				h.put("userType", user.getUser_type());
				list.add(h);
			}

			
			total = userDao.loadDeptUserCount(-id);

		}else if(id == 0){
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				Long companyId = userDao.userCompany(userId);
				list = userDao.loadCompanyUser(companyId, pageIndex, rows);
				
				total = userDao.loadCompanyUserCount(companyId);
			}
			
		}else if(id == -1){
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				Long companyId = userDao.userCompany(userId);
				list = userDao.loadCompanyUserNoDept(companyId, pageIndex, rows);
				
				total = userDao.loadCompanyUserCountNoDept(companyId);
			}
		}
		jsonObject.put("rows", list);
		//records当前页展示的数量
		jsonObject.put("records", total);
		//total 总页数
		total = total%rows==0? total/rows: total/rows+1;
		jsonObject.put("total", total);
		try{
			response.getWriter().write(jsonObject.toJSONString());
		}catch(Exception e){
			System.out.println("............log/loadLogModuleData.do异常！");
		}

	}
	//id:userId--查询的userId 并非session的
	@RequestMapping("/loadUserInforByUserId.do")
	@ResponseBody
	public HashMap<String, Object> loadUserInforByUserId(Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		User user = userDao.loadUserById(id);
		result.put("userName", user.getDisplay_name()==null?user.getLogin_name():user.getDisplay_name());
		if(user.getDept() != null){
			Dept dept = deptDao.loadDeptById(user.getDept());
			result.put("deptName", dept.getName());
		}else{
			result.put("deptName", "未分配部门");
		}
		String position = "普通员工";
		if(user.getUser_type() != null){
			if(user.getUser_type() == UserType.CompanyDeptManager.getValue()){
				position = "部门经理";
			}else if(user.getUser_type() == UserType.CompanyManager.getValue()){
				position = "公司管理员";
			}
		}
		result.put("position", position);
		String userStatus = "";
		if(user.getStatus() != null){
			for(UserStatus type : UserStatus.values()){
				if(type.getValue() == user.getStatus()){
					userStatus = "【"+type.getExplain()+"】";
				}
			}
		}
		result.put("userStatus", userStatus);
		PersonalInformation p = pInformationDao.loadPersonalInformation(id);
		if(p != null){
			result.put("pi", p);
		}
		result.put("userType", userDao.userType(id));
		
		return result;
	}
	@RequestMapping("/addCompany.do")
	@ResponseBody
	public void addCompany(Long id, HttpServletRequest request, HttpServletResponse response){
		try{
			request.setAttribute("id", id);
			request.getRequestDispatcher("/jsp/user/addCompany.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/addCompanyByAdmin.do")
	@ResponseBody
	public HashMap<String, Object> addCompanyByAdmin(String name, String shortName,String loginName,String displayName,
			HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(userDao.checkName(loginName) != 0){
			result.put("msg", "该账号已存在！");
		}else{
			Company company = new Company();
			company.setName(name);
			company.setShort_name(shortName);
			company.setStatus(CommonStatus.Normal.getValue());
			company.setUser(1l);
			deptDao.saveCompany(company);
			User user = new User();
			user.setCompany(company.getId());
			
			user.setLogin_name(loginName);
			user.setUser_type(UserType.CompanyManager.getValue());
			user.setStatus(CommonStatus.Normal.getValue());
			user.setPassword(Md5.md5("123456"));
			user.setDisplay_name(displayName);
			userDao.save(user);
			result.put("msg", "添加成功！");
		}
		return result;
	}
	@RequestMapping("/loadApprovalById.do")
	@ResponseBody
	public void loadApprovalById(Long id, HttpServletRequest request, HttpServletResponse response){
		try{
			request.setAttribute("id", id);
			request.getRequestDispatcher("/jsp/user/viewApproval.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/viewApprovalById.do")
	@ResponseBody
	public HashMap<String, Object> viewApprovalById(Long id, HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Approval approval = approvalDao.viewApprovalById(id);
		Long userId = approval.getUser();
		String userName = userDao.loadName(userId);
		for(UserStatus userStatus : UserStatus.values()){
			if(userStatus.getValue() == approval.getType()) result.put("typeName", userStatus.getExplain());
		}
		result.put("userName", userName);
		result.put("approval", approval);
		return result;
	}
	@RequestMapping("/deadApprovalById.do")
	@ResponseBody
	public HashMap<String, Object> deadApprovalById(Long id, Integer status, 
			HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Approval approval = approvalDao.viewApprovalById(id);
		approval.setStatus(status);
		approvalDao.updateApproval(approval);
		
		if(status == UserStatus.Agree.getValue()){
			User user = userDao.loadUserById(approval.getUser());
			user.setStatus(approval.getType());
			userDao.updateUserStatus(user);
		}
		result.put("msg", "操作成功！");
		return result;
	}
	@RequestMapping("/addEmployeeInformation.do")
	@ResponseBody
	public void addEmployeeInformation(Long userId,
			HttpServletRequest request, HttpServletResponse response){
		try{
			request.setAttribute("uId", userId);
			request.getRequestDispatcher("/jsp/user/addEmployeeInformation.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/cardList.do")
	@ResponseBody
	public void cardList(HttpServletRequest request, HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/user/cardList.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/cardListData.do")
	@ResponseBody
	public void cardListData(String yearMonthDay, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response){
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		jsonObject.put("page", page);
		if(page == null) page = 0;
		if(rows == null) rows = 20;
		Integer pageIndex = (page-1) * rows;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			if(yearMonthDay == null) yearMonthDay = sdf1.format(new Date());
			Long companyId = userDao.userCompany(userId);
			List<HashMap<String, Object>> list = approvalDao.loadCard(companyId, yearMonthDay, pageIndex, rows);
			List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(int i = 0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				if(map.get("cardTime") == null){
					map.put("cardMsg", "<span style='color:red;'>未签到</span>");
					map.put("time", "");
				}else{
					map.put("cardMsg", "<span style='color:green;'>已签到</span>");
					Long time = Long.parseLong(map.get("cardTime").toString());
					
					//String time = ;
					map.put("time", sdf.format(new Date(time*1000)));
					
				}
				data.add(map);
			}
			jsonObject.put("rows", data);
			
			Integer total = userDao.loadCompanyUserCount(companyId);
			//records当前页展示的数量
			jsonObject.put("records", total);
			//total 总页数
			total = total%rows==0? total/rows: total/rows+1;
			jsonObject.put("total", total);
		}


		try{
			response.getWriter().write(jsonObject.toJSONString());
		}catch(Exception e){
			System.out.println("............log/loadLogModuleData.do异常！");
		}

	}
	@RequestMapping("/addDept.do")
	@ResponseBody
	public void addDept(Long deptId,
			HttpServletRequest request, HttpServletResponse response){
		try{
			request.setAttribute("deptId", deptId);
			request.getRequestDispatcher("/jsp/user/addDept.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/saveEmployeeInformation.do")
	@ResponseBody
	public HashMap<String, Object> saveEmployeeInformation(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();

		
	    if(request.getSession().getAttribute("userId") != null){
	    	Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
	    	Long companyId = userDao.userCompany(userId);
	    	Long uId = Long.parseLong(request.getParameter("uId").toString());
	    	PersonalInformation pi = pInformationDao.loadPersonalInformation(uId);
	    	if(pi == null){
	    		pi = new PersonalInformation();
		    	pi.setCompany(companyId);
		    	pi.setStatus(CommonStatus.Normal.getValue());
		    	pi.setUser_type(UserType.CompanyCommon.getValue());
		    	pi.setUser(uId);
	    	}
	    	String sex = request.getParameter("sex");
			if(!"".equals(sex)) pi.setSex(sex.toString());
			
			String phone = request.getParameter("phone");
			if(!"".equals(phone)) pi.setPhone(phone);
			
			String adress = request.getParameter("adress");
			if(!"".equals(adress)) pi.setAdress(adress);
			
			String city = request.getParameter("city");
			if(!"".equals(city)) pi.setCity(city);
			
			String democratic = request.getParameter("democratic");
			if(!"".equals(democratic)) pi.setDemocratic(democratic);
			
			String age = request.getParameter("age");
			if(!"".equals(age)) pi.setAge(Integer.parseInt(age));
			
			String height = request.getParameter("height");
			if(!"".equals(height)) pi.setHeight(Double.parseDouble(height));
			
			String weight = request.getParameter("weight");
			if(!"".equals(weight))  pi.setWeight(Double.parseDouble(weight));
			
			String identity_card = request.getParameter("identity_card");
			if(!"".equals(identity_card)) pi.setIdentity_card(identity_card);
	    	
			
			if(pi.getId() == null){
				pInformationDao.save(pi);
			}else {
				pInformationDao.updatePersonInfor(pi);
			}
			result.put("status", CommonStatus.Normal.getValue());

	    }else {
	    	result.put("status", 2);
		}



		return result;
	}
}
