package QuanDiary.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.Util.enumUtil.UserType;
import QuanDiary.Util.md5Util.Md5;
import QuanDiary.dao.UserDao;
import QuanDiary.entity.User;
import QuanDiary.service.UserService;
import QuanDiary.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class userController {

	@Resource
	UserDao userDao;
	@Resource
	UserServiceImpl userService;
	
	@RequestMapping("/register.do")
	@ResponseBody
	public HashMap<String, String> register(String loginName, String password, String displayName){
		HashMap<String, String> result = new HashMap<String, String>();
		if (userService.checkName(loginName)){
			if (userService.save(loginName, password, displayName)){
				result.put("status", ""+CommonStatus.Normal.getValue());
				result.put("msg", "登录成功！");
			}else {
				result.put("status", ""+CommonStatus.Defeated.getValue());
				result.put("msg", "密码错误！");
			}
		}else {
			result.put("status", ""+CommonStatus.Defeated.getValue());
			result.put("msg", "该账号不存在！");
		}
		return result;
	}
	@RequestMapping("/login.do")
	@ResponseBody
	public HashMap<String, String> login(HttpServletRequest request, String loginName, String password,
						String code, HttpServletResponse response){
		response.setHeader("content-type", "text/html;charset=utf-8");
		HashMap<String, String> result = new HashMap<String, String>();
		HashMap<String, String> data = userService.login(loginName, password);
		if (data.get("userId") == null){
			result.put("status", ""+CommonStatus.Defeated.getValue());
			result.put("msg", data.get("msg"));
		}else {
			result.put("status", ""+CommonStatus.Normal.getValue());
			result.put("msg", data.get("msg"));
			request.getSession().setAttribute("userId", data.get("userId"));
		}
		
		return result;
		
	}
	@RequestMapping("/checkLoginName.do")
	@ResponseBody
	public HashMap<String, Object> checkLoginName(HttpServletRequest request, String loginName,HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(userDao.checkName(loginName) != 0){
			result.put("status", 0);
			result.put("msg", "登录名已存在！");
		}else{
			result.put("status", CommonStatus.Normal.getValue());
			result.put("msg", "登录名可用！");
		}
		return result;
		
	}
	@RequestMapping("/jsp.do")
	@ResponseBody
	public String jsp(HttpServletRequest request, String loginName, String password,
			String code, HttpServletResponse response){
		try{
			HttpSession session = request.getSession();
			if (session != null){
				System.out.println("..............userId:"+session.getAttribute("userId"));
			}
			//request.setAttribute("userName", loginName);
			//request.setAttribute("password", password);
			response.sendRedirect("/QuanDiary/jsp/menu.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/QuanDiary/jsp/menu";
	}
	@RequestMapping("/myInfor.do")
	@ResponseBody
	public void myInfor(HttpServletRequest request, HttpServletResponse response){
		try{
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				request.setAttribute("id", userId);
			}

			request.getRequestDispatcher("/jsp/user/employeeInformation.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	@RequestMapping("/addEmployee.do")
	@ResponseBody
	public void addEmployee(HttpServletRequest request, HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/user/addEmployee.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/userChangePassword.do")
	@ResponseBody
	public void userChangePassword(HttpServletRequest request, HttpServletResponse response){
		try{
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				User user = userDao.loadUserById(userId);
				request.setAttribute("loginName", user.getLogin_name());
			}
			request.getRequestDispatcher("/jsp/user/changePassword.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/userChangePasswordById.do")
	@ResponseBody
	public HashMap<String, Object> userChangePasswordById(HttpServletRequest request, HttpServletResponse response,
			String oldPassword, String newPassword){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			User user = userDao.loadUserById(userId);
			if(user.getPassword().equals(Md5.md5(oldPassword))){
				user.setPassword(Md5.md5(newPassword));
				userDao.updateUserStatus(user);
				result.put("msg", "密码修改成功！");
				result.put("status", CommonStatus.GoToLogin.getValue());
			}else{
				result.put("msg", "原密码错误！");
				result.put("status", 2);
			}
		}
		return result;
	}
	@RequestMapping("/employee.do")
	@ResponseBody
	public void employee(HttpServletRequest request, HttpServletResponse response){
		try{
			//request.setAttribute("data", userService.loadDeptData("",request,response));
			request.getRequestDispatcher("/jsp/user/employee.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping(value="/addEmployeeByFile.do" ,method=RequestMethod.POST)
	@ResponseBody
	public void addEmployeeByFile(MultipartFile file, 
			HttpServletRequest request, HttpServletResponse response)throws IOException{
		//String path = request.getSession().getServletContext().getRealPath("upload");
		String msg = "";
		try{
			List<String[]> result = userService.readExcel(file);

			Integer successCount = 0;
			Integer errorCount = 0;
			List<String> successUserName = new ArrayList<String>();
			List<String> errorUserName = new ArrayList<String>();
			List<User> addSuccessUser = new ArrayList<User>();

			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				Long companyId = userDao.userCompany(userId);


				for(String[] re : result){

					if(userService.checkName(re[0].trim())){
						successUserName.add(re[1]);
						successCount ++;

						User user = new User();
						user.setCompany(companyId);
						user.setLogin_name(re[0]);
						user.setDisplay_name(re[1]);
						user.setPassword(Md5.md5("123456"));
						user.setStatus(CommonStatus.Normal.getValue());
						user.setUser_type(UserType.CompanyCommon.getValue());
						addSuccessUser.add(user);

					}else{

						errorUserName.add(re[1]);
						errorCount ++ ;
					}
					
					
					
				}
				if(addSuccessUser.size() > 0) userDao.addEmployeeBatch(addSuccessUser);
			}
			
			msg += "本次共导入："+result.size()+"个！";
			msg += "其中成功："+successCount+"个，";
			msg += "失败："+errorCount+"个！<br>";

			if(errorUserName.size() > 0){
				msg += "失败：<br>";
				for(int i = 0;i<errorUserName.size();i++){
					msg += errorUserName.get(i)+",原因：登录名被占用！<br>";
				}
			}
			
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/jsp/message.jsp").forward(request,response);
			

		}catch (IOException e) {
			request.setAttribute("msg", "文件格式错误！");


		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("msg", "系统错误！");
			System.out.println("....../user/addEmployeeByFile.do.上传文件出现了异常");


			
		}
		try{
			request.getRequestDispatcher("/jsp/message.jsp").forward(request,response);
		}catch(Exception e){
			
		}
		

		
	}
	@RequestMapping("/download.do")
	@ResponseBody
    public void download(HttpServletRequest request,HttpServletResponse response){  
        try {
            //String fileName = request.getSession().getServletContext().getRealPath("upload")+"/101.jpg";  
            
            InputStream bis = new BufferedInputStream(new FileInputStream(new File("E:\\QuanDiaryFile\\admin\\添加员工模板.xlsx")));  
              
            String filename = "添加员工模板.xlsx";  
            
            filename = URLEncoder.encode(filename,"UTF-8");  
            
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
                
            response.setContentType("multipart/form-data");   
            
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
            int len = 0;  
            while((len = bis.read()) != -1){  
                out.write(len);  
                out.flush();  
            }  
            out.close();  
		} catch (Exception e) {
			System.out.println("...........终止下载文件！");
		}

    }  
	@RequestMapping("/addEmployeeBatch.do")
	@ResponseBody
	public void addEmployeeBatch(){
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		for(int i=0;i<3;i++){
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("loginName", "Batch"+i);
			map.put("displayName", "Batch"+i);
			map.put("password", Md5.md5("123456"));
			map.put("status", CommonStatus.Normal.getValue());
			map.put("userType", UserType.CompanyCommon.getValue());
			users.add(map);
		}
		userService.addEmployeeBatch(users);
	}
	@RequestMapping("/addEmployeeByManager.do")
	@ResponseBody
	public HashMap<String, Object> addEmployeeByManager(String loginName, String displayName,
			Long deptId, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(loginName != null && displayName != null && deptId != null){
			if(userService.checkName(loginName)){
				User user = new User();
				if(request.getSession().getAttribute("userId") != null){
					Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
					Long companyId = userDao.userCompany(userId);
					
					if(deptId == 0 || deptId == -1){
						deptId = null;
					}else {
						deptId = -deptId;
					}
					user.setUser_type(UserType.CompanyCommon.getValue());
					user.setDept(deptId);
					user.setCompany(companyId);
					user.setStatus(CommonStatus.Normal.getValue());
					user.setPassword(Md5.md5("123456"));
					user.setLogin_name(loginName);
					user.setDisplay_name(displayName);
					userDao.save(user);
					result.put("status", CommonStatus.Normal.getValue());
					result.put("msg", "添加成功！");
				}else {
					result.put("status", CommonStatus.GoToLogin.getValue());
					result.put("msg", "请重新登录！");
				}

			}else{
				result.put("status", 2);
				result.put("msg", "该账号已被占用！");
			}
		}else {
			result.put("status", 2);
			result.put("msg", "数据错误！");
		}

		return result;
	}



	@RequestMapping("/readExcel.do")
	@ResponseBody
	public void readExcel(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
