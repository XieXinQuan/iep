package QuanDiary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import QuanDiary.Util.enumUtil.UserType;
import QuanDiary.dao.UserDao;
import QuanDiary.entity.Menu;
import QuanDiary.entity.SecondMenu;
import QuanDiary.entity.User;
import QuanDiary.service.IndexService;
import QuanDiary.service.UserService;
import QuanDiary.service.UserServiceImpl;

@Controller
@RequestMapping("/index")
public class indexController {
	@Resource
	UserDao userDao;
	@Resource
	IndexService indexService;
	@Resource
	UserServiceImpl userService;
	@RequestMapping("/loadMenu.do")
	@ResponseBody
	public HashMap<String, Object> loadMenu(HttpServletRequest request, HttpServletResponse response){
		//response.setHeader("content-type", "text/html;charset=utf-8");
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if (request.getSession().getAttribute("userId") == null){
			result.put("status", 0);
			List<Menu> menus = new ArrayList<Menu>();
			result.put("menus", menus);
		}else {
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Integer userType = userService.userType(userId);
			List<Menu> menus = indexService.loadMenu(userType);
			result.put("status", 1);
			result.put("menus", menus);
		}
		return result;
	}
	@RequestMapping("/loadSecondMenu.do")
	@ResponseBody
	public HashMap<String, Object> loadSecondMenu(HttpServletRequest request, Long menu){

		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if (request.getSession().getAttribute("userId") == null || menu == null){
			result.put("status", 0);
			result.put("secondMenu", new ArrayList<SecondMenu>());
		} else {
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			//Long menuId = indexService.findMenu(menu);
			//System.out.println("..........menuId:"+menuId);
			//if (menuId != null){
				result.put("status", 1);
				Integer userType = userService.userType(userId);
				result.put("secondMenu", indexService.loadSecondMenu(menu, userType));
			//}else {


		}
		return result;
	}
	@RequestMapping("/getDept.do")
	@ResponseBody
	public void getDept(HttpServletRequest request, HttpServletResponse response){

		request.setAttribute("user", "555");
		request.setAttribute("user1", "5555");
		try{
			request.getRequestDispatcher("/jsp/dept.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(".......�˴������쳣��");
		}
	}
	@RequestMapping("/test.do")
	@ResponseBody
	public void test(){
		List<Map<String, Object>> maps = userService.testMap();
		if (maps.size() > 0){
			for (int i=0;i<maps.size();i++){
				Map<String, Object> map = maps.get(i);
				Set<String> keys = map.keySet();
				String syso = ".......";
				for(String key : keys){
					syso += "key:"+key+"....value:"+map.get(key)+"....";
					//System.out.println("......key:"+key+",value:"+map.get(key));
				}
				System.out.println(syso.substring(0, syso.length()-4));
			}
		}
	}
	@RequestMapping("/testParameters.do")
	@ResponseBody
	public void testParameters(){
		List<Map<String, Object>> maps = userService.testParameters1("%quan%", 1);
		if (maps.size() > 0){
			for (int i=0;i<maps.size();i++){
				Map<String, Object> map = maps.get(i);
				Set<String> keys = map.keySet();
				String syso = ".......";
				for(String key : keys){
					syso += "key:"+key+"....value:"+map.get(key)+"....";
					//System.out.println("......key:"+key+",value:"+map.get(key));
				}
				System.out.println(syso.substring(0, syso.length()-4));
			}
		}
	}
	@RequestMapping("/loadName.do")
	@ResponseBody
	public HashMap<String, Object> loadName(HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		result.put("status", 0);
		//System.out.println(userId);
		if (request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			String name = userService.loadName(userId);
			if (name != null) {
				User user = userDao.loadUserById(userId);
				for(UserType userType : UserType.values()){
					if(userType.getValue() == user.getUser_type()) result.put("userType", userType.getExplain());
				}
				result.put("status", 1);
				result.put("name", name);
			}
			
		}
		return result;
		
		
		
	}
	
}
