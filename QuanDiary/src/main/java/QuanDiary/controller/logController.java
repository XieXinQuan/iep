package QuanDiary.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;












































import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.Util.enumUtil.LogNoticeType;
import QuanDiary.Util.enumUtil.LogStatus;
import QuanDiary.Util.enumUtil.UserStatus;
import QuanDiary.Util.enumUtil.UserType;
import QuanDiary.Util.toolUtil.JsonAnalysis;
import QuanDiary.Util.toolUtil.TranscodingUtil;
import QuanDiary.dao.LogContentDao;
import QuanDiary.dao.LogDao;
import QuanDiary.dao.LogLikeDao;
import QuanDiary.dao.LogTemplateDao;
import QuanDiary.dao.UserDao;
import QuanDiary.entity.Comment;
import QuanDiary.entity.Log;
import QuanDiary.entity.LogContent;
import QuanDiary.entity.LogLike;
import QuanDiary.entity.LogNotice;
import QuanDiary.entity.LogRead;
import QuanDiary.entity.LogTemplate;
import QuanDiary.entity.Menu;
import QuanDiary.entity.SecondMenu;
import QuanDiary.entity.TemplateTag;
import QuanDiary.entity.User;
import QuanDiary.service.IndexService;
import QuanDiary.service.LogContentService;
import QuanDiary.service.LogService;
import QuanDiary.service.LogTemplateService;
import QuanDiary.service.TemplateTagService;
import QuanDiary.service.UserService;
import QuanDiary.service.UserServiceImpl;

@Controller
@RequestMapping("/log")
public class logController {
	@Resource
	LogContentDao logContentDao;
	@Resource
	LogDao logDao;
	@Resource
	LogLikeDao logLikeDao;
	@Resource
	LogTemplateDao logTemplateDao;
	@Resource
	UserDao userDao;
	@Resource
	IndexService indexService;
	@Resource
	UserServiceImpl userService;
	@Resource
	LogService logService;
	@Resource
	LogContentService logContentService;
	@Resource
	LogTemplateService logTemplateService;
	@Resource
	TemplateTagService templateTagService;
	@RequestMapping("/loadLogModule.do")
	@ResponseBody
	public void loadLogModule(HttpServletRequest request,HttpServletResponse response,
						Integer status, Long deptId){
		
		if (request.getSession().getAttribute("userId") != null){
			request.setAttribute("userId", Long.parseLong(request.getSession().getAttribute("userId").toString()));
		}
		try{
			request.getRequestDispatcher("/jsp/log/logModule.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/logWritingSituation.do")
	@ResponseBody
	public void logWritingSituation(HttpServletRequest request,HttpServletResponse response){
		
		try{
			request.getRequestDispatcher("/jsp/log/logWritingSituation.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/addLogNotice.do")
	@ResponseBody
	public void addLogNotice(HttpServletRequest request,HttpServletResponse response){
		
		try{
			request.getRequestDispatcher("/jsp/log/addLogNotice.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/saveLogNotice.do")
	@ResponseBody
	public HashMap<String, Object> saveLogNotice(String content, String title, Long id,
			HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long company = userDao.userCompany(userId);
			LogNotice notice = new LogNotice();
			notice.setCompany(company);
			notice.setContent(content);
			notice.setTitle(title);
			notice.setStatus(CommonStatus.Normal.getValue());
			notice.setCreate_time(new Timestamp(System.currentTimeMillis()));
			if(id != null){
				if(id > 0){	//id>0表明是userId--
					notice.setType(LogNoticeType.User.getValue());
					notice.setUser(id);
				}else if(id == 0){	//公司全体
					notice.setType(LogNoticeType.Company.getValue());
				}else if(id < -1){
					notice.setType(LogNoticeType.Dept.getValue());
					notice.setDept(-id);
				}
			}
			logDao.saveLogNotice(notice);
		}

		result.put("msg", "发布成功！");
		return result;
		
	}
	@RequestMapping("/viewLogNotice.do")
	@ResponseBody
	public void viewLogNotice(Long id, HttpServletRequest request,HttpServletResponse response){
		
		try{
			request.setAttribute("id", id);
			request.getRequestDispatcher("/jsp/log/viewLogNotice.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/viewLogNoticeById.do")
	@ResponseBody
	public HashMap<String, Object> viewLogNoticeById(Long id, HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		LogNotice notice = logDao.loadLogNoticeById(id);
		result.put("notice", notice);
		return result;
		
	}
	@RequestMapping("/logNoticeList.do")
	@ResponseBody
	public void logNoticeList(HttpServletRequest request,HttpServletResponse response){
		
		try{
			Long userId = userService.getCurrUser(request);
			
			if(userId != 0l) request.setAttribute("userType", userDao.userType(userId));
			
			request.getRequestDispatcher("/jsp/log/logNoticeList.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	
	@RequestMapping("/delLogNotice.do")
	@ResponseBody
	public HashMap<String, Object> delLogNotice(Long id, HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		LogNotice notice = logDao.loadLogNoticeById(id);
		notice.setStatus(CommonStatus.Delete.getValue());
		logDao.delLogNotice(notice);
		result.put("msg", "删除成功！");
		return result;
		
	}
	@RequestMapping("/logNoticeListData.do")
	@ResponseBody
	public void logNoticeListData(Integer page, Integer rows, 
			HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		if(request.getSession().getAttribute("userId") != null){

			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Integer userType = userDao.userType(userId);
			Long companyId = null;
			Long deptId = null;
			Long uId = null;
			User user = userDao.loadUserById(userId);
			if(userType != UserType.Admin.getValue()) {
				companyId = user.getCompany();
				if(userType != UserType.CompanyManager.getValue()){
					deptId = user.getDept();
					uId = user.getId();
				}
			}
			
			Integer pageIndex = (page-1) * rows;
			List<HashMap<String, Object>> list = logDao.loadLogNotice(companyId, deptId, uId, pageIndex, rows);
			for(HashMap<String, Object> map : list){
				String name = "公司全体人员";
				if(map.containsKey("deptName")) name = map.get("deptName").toString();
				if(map.containsKey("userName")) name = map.get("userName").toString();
				map.put("name", name);
			}
			jsonObject.put("rows", list);

			
			Integer total = logDao.loadLogNoticeCount(companyId, deptId, uId);
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
	@RequestMapping("/logWritingSituationData.do")
	@ResponseBody
	public void logWritingSituationData(String time, Integer page, Integer rows, 
			HttpServletRequest request,HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		if(request.getSession().getAttribute("userId") != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			if(time == null) time = sdf.format(new Date());
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long companyId = userDao.userCompany(userId);
			Integer pageIndex = (page-1) * rows;
			List<HashMap<String, Object>> list = logDao.logWritingSituation(time, UserStatus.AskForLeave.getValue(), companyId, pageIndex, rows);
			
			jsonObject.put("rows", list);

			
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
	@RequestMapping("/viewDiaryModule.do")
	@ResponseBody
	public void viewDiaryModule(HttpServletRequest request,HttpServletResponse response,
						Long id){
		

		request.setAttribute("id", id);

		try{
			request.getRequestDispatcher("/jsp/log/viewDiaryModule.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/viewDiaryModuleById.do")
	@ResponseBody
	public HashMap<String, Object> viewDiaryModuleById(HttpServletRequest request,HttpServletResponse response,
						Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("log", logTemplateDao.loadLogTemplateById(id));
		result.put("tags", templateTagService.loadModuleById(id));
		return result;
	}
	@RequestMapping("/addLogModule.do")
	@ResponseBody
	public void addLogModule(HttpServletRequest request,HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/log/addLogModule.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/sumbitLogModule.do")
	@ResponseBody
	public HashMap<String, Object> sumbitLogModule(HttpServletRequest request, 
			String content, String logTitle){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		Long companyId = userService.userCompany(userId);
		Long id = logTemplateService.save(companyId, logTitle);
		//System.out.println(id);
		//JSONObject jsonObject = JSONObject.fromObject(TranscodingUtil.TranCode(content));
		//System.out.println("..........."+jsonObject.isArray());
		String data = content;
		data = data.substring(1, data.length()-1);
		String [] jsonData = data.split("},");
		List<TemplateTag> templateTags = new ArrayList<TemplateTag>();
		for (int i=0;i<jsonData.length;i++){
			if(i!=jsonData.length-1){
				jsonData[i] = jsonData[i]+"}";
			}
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonData[i]);
			TemplateTag tag = new TemplateTag();
			tag.setTemplate(id);
			tag.setTitle(jsonObject.get("title").toString());
			tag.setStatus(CommonStatus.Normal.getValue());
			tag.setOrder_no(jsonObject.getInt("orderNo"));
			tag.setCreatr_time(new Timestamp(System.currentTimeMillis()));
			tag.setType(jsonObject.getInt("type"));
			tag.setContent(jsonObject.getString("data1"));
			templateTags.add(tag);
		}
		if (templateTags.size() > 0){
			templateTagService.saveByBatch(templateTags);
		}
		
		return result;
	}
	@RequestMapping("/selectType.do")
	@ResponseBody
	public void selectType(HttpServletRequest request,HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/log/selectType.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/addDiary.do")
	@ResponseBody
	public void addDiary(HttpServletRequest request,HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/log/addDiary.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/addDiary.do异常！");
		}
	}
	@RequestMapping("/loadModuleId.do")
	@ResponseBody
	public HashMap<String, Object> loadModuleId(HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if (request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long company = userService.userCompany(userId);
			List<LogTemplate> templates = logTemplateService.loadModuleByCompany(company, CommonStatus.Normal.getValue());
			List<HashMap<String, Object>> content = new ArrayList<HashMap<String,Object>>();
			for(LogTemplate template : templates){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", template.getId());
				map.put("title", template.getTitle());
				content.add(map);
			}
			if (content.size() > 0){
				result.put("status", 1);
				result.put("content", content);
			}else {
				result.put("status", 2);
			}
			
		}else {
			result.put("status", CommonStatus.GoToLogin.getValue());
		}
		return result;
	}
	@RequestMapping("/loadModuleById.do")
	@ResponseBody
	public HashMap<String, Object> loadModuleById(HttpServletRequest request,HttpServletResponse response,
			Long moduleId){
		HashMap<String, Object> result = new HashMap<String, Object>();


		List<TemplateTag> tags = templateTagService.loadModuleById(moduleId);

		List<TemplateTag> content = new ArrayList<TemplateTag>();
		for(TemplateTag tag : tags){
			content.add(tag);
		}
		if (tags.size() > 0){

			result.put("status", CommonStatus.Normal.getValue());
			
			result.put("content", tags);
		}else {
			result.put("status", 2);
		}

		return result;
	}
	@RequestMapping("/loadLogModuleData.do")
	@ResponseBody
	public void loadLogModuleData(HttpServletRequest request,HttpServletResponse response,
						Integer status, Long deptId, Integer page, Integer rows){
		//System.out.println("............page:"+page);
		//System.out.println("............rows:"+rows);
		response.setHeader("content-type", "text/html;charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		if (page != null){
			jsonObject.put("page", page);
		}else {
			jsonObject.put("page", 1);
		}
		
		
		Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
		Long companyId = userService.userCompany(userId);
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		if (companyId != null){
			Integer pageIndex = (page-1) * rows;
			List<LogTemplate> templates = logTemplateService.loadModule(companyId, CommonStatus.Normal.getValue(),
					CommonStatus.Disabled.getValue(),pageIndex, rows);
			for (LogTemplate template : templates){
				HashMap<String, Object> h = new HashMap<String, Object>();
				h.put("id", template.getId());
				h.put("title", template.getTitle());
				h.put("status", template.getStatus());
				list.add(h);
			}

			jsonObject.put("rows", list);

			
			Integer total = logTemplateService.countByLoadModule(companyId, CommonStatus.Normal.getValue(),
					CommonStatus.Disabled.getValue());
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
	@RequestMapping("/loadOtherUserLogList.do")
	@ResponseBody
	public void loadOtherUserLogList(Integer type, HttpServletRequest request,HttpServletResponse response){
		try{
			request.setAttribute("type", type==null?0:type);
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				request.setAttribute("userType", userDao.userType(userId));
			}
			request.getRequestDispatcher("/jsp/log/otherUserDiary.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/addDiary.do异常！");
		}
	}
	//id:正数：userId 负数：deptId 0:公司 -1：未分配部门
	@RequestMapping("/loadOtherUserLogListData.do")
	@ResponseBody
	public void loadOtherUserLogListData(HttpServletRequest request,HttpServletResponse response,
						Integer status, Long id, Integer page, String time, String startTime, String endTime, Integer rows){
		JSONObject jsonObject = new JSONObject();
		if (page != null){
			jsonObject.put("page", page);
		}else {
			jsonObject.put("page", 1);
		}
		
		if(request.getSession().getAttribute("userId") != null){
			Long currUserId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			User user = userDao.loadUserById(currUserId);
			if(UserType.Admin.getValue() == userDao.userType(currUserId)){
				Integer pageIndex = (page-1) * rows;
				Long companyId = id == null ? null : id == 0 ? null : id;
				List<HashMap<String, Object>> result = logDao.logListDataByAdmin(companyId, time == null ? null : "".equals(time) ? null:time, pageIndex, rows);
				jsonObject.put("rows", result);
				Integer total = logDao.countLogListDataByAdmin(companyId, time == null ? null : "".equals(time) ? null:time);
				//records当前页展示的数量
				jsonObject.put("records", total);
				//total 总页数
				total = total%rows==0? total/rows: total/rows+1;
				jsonObject.put("total", total);
				

			}else{
				Integer pageIndex = (page-1) * rows;
				Long target = null;
				Long dept = null;
				Boolean isNoDept = null;
				if(id != null && id != 0){
					if(id > 0){
						target = id;
					}else if(id == -1){
						isNoDept = true;
					}else{
						dept = -id;
					}
				}
				
				List<HashMap<String, Object>> result = logDao.loadOtherUserLogIsCompany(user.getCompany(), 
						currUserId, target, dept, isNoDept, time == null ? null : "".equals(time) ? null:time, 
								startTime, endTime, pageIndex, rows);
				
				//List<HashMap<String, Object>> result = logDao.loadOtherUserLogIsCompany(user.getCompany(), currUserId, pageIndex, rows);
				jsonObject.put("rows", result);
				Integer total = logDao.countByLoadOtherUserLogIsCompany(user.getCompany(), 
						currUserId, target, dept, isNoDept, time == null ? null : "".equals(time) ? null:time,
								startTime, endTime);
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
	}
	@RequestMapping("/diaryExportExcel.do")
	@ResponseBody
	public void diaryExportExcel(String ids,Long id, Integer page, String time, 
			String startTime, String endTime, 
			HttpServletRequest request,HttpServletResponse response){
        //获取数据

		Long target = null;
		Long dept = null;
		Boolean isNoDept = null;
		if(id != null && id != 0){
			if(id > 0){
				target = id;
			}else if(id == -1){
				isNoDept = true;
			}else{
				dept = -id;
			}
		}
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long companyId = userDao.userCompany(userId);
			List<HashMap<String, Object>> result = logDao.loadOtherUserLogIsCompanyByExportExcel(companyId, 
					userId, target, dept, isNoDept, time == null ? null : "".equals(time) ? null:time, 
							startTime, endTime);
			//模板Id Map
			HashSet<Long> moduleIds = new HashSet<Long>();
			HashMap<Long, String> moduleName = new HashMap<Long, String>();
			HashMap<Long, List<Long>> logIdByModuleIds = new HashMap<Long, List<Long>>();
			HashMap<Long, String> userNameByLogId = new HashMap<Long, String>();
			//统计模板Id
			for(HashMap<String, Object> map : result){
				moduleIds.add(Long.parseLong(map.get("templateId").toString()));
				moduleName.put(Long.parseLong(map.get("templateId").toString()), 
						map.get("moduleTitle").toString());
				List<Long> logIds;
				if(logIdByModuleIds.get(Long.parseLong(map.get("templateId").toString())) != null){
					logIds = logIdByModuleIds.get(Long.parseLong(map.get("templateId").toString()));
				}else{
					logIds = new ArrayList<Long>();
				}
				logIds.add(Long.parseLong(map.get("id").toString()));
				logIdByModuleIds.put(Long.parseLong(map.get("templateId").toString()), 
						logIds);
				userNameByLogId.put(Long.parseLong(map.get("id").toString()), 
						map.get("userName").toString());
			}
			HSSFWorkbook wb = null;
			for(Long moduleId : moduleIds){
				wb = logService.getHSSFWorkbook(wb, moduleId, moduleName.get(moduleId), 
						logIdByModuleIds.get(moduleId), userNameByLogId);
				
			}
			if(wb == null){
				wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("无数据");
				HSSFRow row = sheet.createRow(0);
				row.createCell(0).setCellValue("无数据");
			}
			try {
		        String filename = "导出日志.xls";  
		        
		        filename = URLEncoder.encode(filename,"UTF-8");  
		        
		        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
		            
		        response.setContentType("multipart/form-data");   

		        OutputStream os = response.getOutputStream();
		        wb.write(os);
		        os.flush();
		        os.close();
			} catch (Exception ex) {
			    ex.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		/*
		
		LogTemplate logTemplate = logTemplateDao.loadLogTemplateById(9l);
		List<TemplateTag> tags = templateTagService.loadModuleById(9l);
		String [] title = {"表1", "表2", "表3", "表4"};
		for(int i = 0;i<tags.size();i++){
			title[i] = tags.get(i).getTitle();
		}
		
		List<LogContent> list = logContentDao.viewDiary(39l);
		
		
		
		//List<>
		
        //List<PageData> list = reportService.bookList(page);

        //excel标题

		//String fileName = "日志信息表"+System.currentTimeMillis()+".xls";
		//sheet名
		String sheetName = "学生信息表";
		String [][] content = new String[1][10];
		
		content[0] = new String[title.length];
		for(int i = 0; i<title.length;i++){
			content[0][i] = ""+i;
		}
		

		//创建HSSFWorkbook 
		//HSSFWorkbook wb = logService.getHSSFWorkbook(sheetName, title, content, null);
*/

	}
	@RequestMapping("/diaryExport.do")
	@ResponseBody
	public void diaryExport(String ids,
			HttpServletRequest request,HttpServletResponse response){
		
		//空白文档
		XWPFDocument doc = new XWPFDocument();
		
		try {
			
			String saveFileName = "E:\\QuanDiaryFile\\file\\"+System.currentTimeMillis()+".docx";
			
			
			FileOutputStream fos= new FileOutputStream(new File(saveFileName));
			
			//标题
			XWPFParagraph title =doc.createParagraph();
			//设置居中
			title.setAlignment(ParagraphAlignment.CENTER);
 
			XWPFRun titleRun = title.createRun();
			titleRun.setText("资料卡长编55");
			titleRun.setBold(true);
			titleRun.setFontSize(30);

			XWPFParagraph  graph =doc.createParagraph();
			XWPFRun run =graph.createRun();
			
			//小标题
			run.setText("4444444"+ids==null?"56":ids+"\r");
			run.setFontSize(25);
			run.setBold(true);
			
			graph =doc.createParagraph();
			run =graph.createRun();
			
			//资料正文
			run.setText( "  资料正文5：\r");
			run.setFontSize(20);
			run.setBold(true);
			//正文
			graph =doc.createParagraph();
			 run =graph.createRun();
			
			//
			run.setText("  5555555\r");
			run.setFontSize(16);
			
			//资料来源
			graph =doc.createParagraph();
			 run =graph.createRun();
			
			//
			run.setText( "  资料来源:\r");
			run.setFontSize(20);
			run.setBold(true);
			//资料来源
			graph =doc.createParagraph();
			 run =graph.createRun();

			run.setText("  666666\r\r\r\r");
			run.setFontSize(16);

			doc.write(fos);
			fos.close();	
			
	        String filename = "日志.docx";  
	        
	        filename = URLEncoder.encode(filename,"UTF-8");  
	        
	        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
	            
	        response.setContentType("multipart/form-data");   
	        
	        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
	        int len = 0;  
	        InputStream bis = new BufferedInputStream(new FileInputStream(new File(saveFileName))); 
	        while((len = bis.read()) != -1){  
	            out.write(len);  
	            out.flush();  
	        }  
	        bis.close();
	        doc.close();
	        out.close();  
		}catch(Exception e){
			
		}

	}
	@RequestMapping("/viewDiary.do")
	@ResponseBody
	public void viewDiary(HttpServletRequest request,HttpServletResponse response,
			Long id){
		try{

			request.setAttribute("id", id);
			Log log = logService.loadModuleById(id);
			
			request.setAttribute("title", log.getTitle());
			request.setAttribute("createTime", log.getCreate_time());
			request.setAttribute("userName", userService.loadName(log.getUser()));
			
			request.getRequestDispatcher("/jsp/log/viewDiary.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/viewDiary.do异常！");
		}
	}
	@RequestMapping("/viewDiaryComment.do")
	@ResponseBody
	public void viewDiaryComment(HttpServletRequest request,HttpServletResponse response,
			Long id){
		try{
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				request.setAttribute("userType", userDao.userType(userId));
			}
			
			
			request.setAttribute("id", id);
			Log log = logService.loadModuleById(id);
			request.setAttribute("title", log.getTitle());
			request.setAttribute("createTime", log.getCreate_time());
			request.setAttribute("userName", userService.loadName(log.getUser()));


			request.getRequestDispatcher("/jsp/log/diaryComment.jsp").forward(request, response);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("............log/viewDiary.do异常！");
		}
	}
	@RequestMapping("/logStatistical.do")
	@ResponseBody
	public void logStatistical(HttpServletRequest request,HttpServletResponse response,
			Long id){
		try{

			request.getRequestDispatcher("/jsp/log/logStatistical.jsp").forward(request, response);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("............log/logStatistical.do异常！");
		}
	}
	@RequestMapping("/logStatisticalData.do")
	@ResponseBody
	public void logStatisticalData(HttpServletRequest request,HttpServletResponse response,
			Long userId, Long deptId, Long startTime, Long endTime, Integer page, Integer rows, String time){

		if(time == null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			time = sdf.format(new Date());
		}

		JSONObject jsonObject = new JSONObject();
		if (page != null){
			jsonObject.put("page", page);
		}else {
			jsonObject.put("page", 1);
		}


		if(request.getSession().getAttribute("userId") != null){
			Long currUserId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long companyId = userDao.userCompany(currUserId);
			Integer pageIndex = (page-1) * rows;
			List<HashMap<String, Object>> data = logLikeDao.logStatistical(time, companyId, 
					pageIndex, rows);
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
	
	@RequestMapping("/viewDiaryCommentById.do")
	@ResponseBody
	public HashMap<String, Object> viewDiaryCommentById(HttpServletRequest request,HttpServletResponse response,
			Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<HashMap<String, Object>> likes = logLikeDao.loadLogLike(id);
		List<HashMap<String, Object>> reads = logLikeDao.loadLogRead(id);
		result.put("likes", likes);
		result.put("reads", reads);
		List<HashMap<String, Object>> comments = logLikeDao.loadLogComment(id);
		result.put("comments", comments);
		return result;
	}
	@RequestMapping("/delComment.do")
	@ResponseBody
	public HashMap<String, Object> delComment(HttpServletRequest request,HttpServletResponse response,
			Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "操作失败！");
		result.put("msg", 2);
		result.put("status", CommonStatus.Normal.getValue());
		if(id != null){
			Comment comment = logLikeDao.loadCommentById(id);
			comment.setStatus(CommonStatus.Delete.getValue());
			logLikeDao.updateCommentStatus(comment);
			result.put("msg", "删除成功！");
			result.put("status", CommonStatus.Normal.getValue());
		}
		
		return result;
	}
	@RequestMapping("/todayLogWriterFinish.do")
	@ResponseBody
	public HashMap<String, Object> todayLogWriterFinish(HttpServletRequest request,HttpServletResponse response,
			Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "");
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			if(userDao.userType(userId) == UserType.CompanyCommon.getValue()){
				Integer count = logDao.todayIsFinishLog(userId, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				result.put("msg", count == 0? "您今天还没完成日志！":"");
			}
		}

		return result;
	}
	
	@RequestMapping("/sumbitDiaryCommentContent.do")
	@ResponseBody
	public HashMap<String, Object> sumbitDiaryCommentContent(HttpServletRequest request,HttpServletResponse response,
			Long id, String content){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(id != null){
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				Comment comment = new Comment();
				comment.setLog(id);
				comment.setCreate_time(new Timestamp(System.currentTimeMillis()));
				comment.setStatus(CommonStatus.Normal.getValue());
				comment.setUser(userId);
				comment.setContent(content);
				logLikeDao.saveLogComment(comment);
				result.put("status", CommonStatus.Normal.getValue());
				String commentUserName = userDao.loadName(userId);
				result.put("commentUserName", commentUserName);
			}
		}
		return result;
	}
	@RequestMapping("/viewDiaryById.do")
	@ResponseBody
	public HashMap<String, Object> viewDiaryById(HttpServletRequest request,HttpServletResponse response,
			Long id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		Log log = logDao.loadModuleById(id);
		result.put("log", log);
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			if(!log.getUser().equals(userId) && logLikeDao.checkLogRead(id, userId) == 0){
				LogRead logRead = new LogRead();
				logRead.setCreate_time(new Timestamp(System.currentTimeMillis()));
				logRead.setLog(id);
				logRead.setUser(userId);
				logRead.setStatus(CommonStatus.Normal.getValue());
				logLikeDao.saveLogRead(logRead);
			}
		}
		
		List<TemplateTag> tags = templateTagService.loadModuleById(logService.loadModuleById(id).getTemplate());
		List<LogContent> content = logContentDao.viewDiary(id);
		
		List<HashMap<String, Object>> lists = new ArrayList<HashMap<String,Object>>();
		for(int i = 0;i<tags.size();i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("title", tags.get(i).getTitle());
			map.put("type", tags.get(i).getType());
			map.put("content", content.get(i).getContent());
			lists.add(map);
		}
		result.put("content", lists);
		return result;
	}
	@RequestMapping("/loadLogList.do")
	@ResponseBody
	public void loadLogList(HttpServletRequest request,HttpServletResponse response){
		try{
			request.getRequestDispatcher("/jsp/log/myDiary.jsp").forward(request, response);
		}catch (Exception e){
			System.out.println("............log/loadLogModule.do异常！");
		}
	}
	@RequestMapping("/loadLogListData.do")
	@ResponseBody
	public void loadLogListData(HttpServletRequest request,HttpServletResponse response,
						Integer status, Long deptId, Integer page, Integer rows
						,Long startTime,Long endTime){
		//System.out.println("............startTime:"+startTime);
		//System.out.println("............endTime:"+endTime);
		response.setHeader("content-type", "text/html;charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		if (page != null){
			jsonObject.put("page", page);
		}else {
			jsonObject.put("page", 1);
		}

		if (request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Integer pageIndex = (page-1) * rows;
			List<Log> logs = logDao.loadMyLog(userId,
					pageIndex, rows);
			jsonObject.put("rows", logs);
			
			
			Integer total = logService.countByLoadMyLog(userId, CommonStatus.Normal.getValue());
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
	@RequestMapping("/loadSecondMenu.do")
	@ResponseBody
	public HashMap<String, Object> loadSecondMenu(HttpServletRequest request, Long menu){
		
		HashMap<String, Object> result = new HashMap<String, Object>();

		if (request.getSession().getAttribute("userId") == null || menu == null){
			result.put("status", 0);
			result.put("secondMenu", new ArrayList<SecondMenu>());
		} else {

			//Long menuId = indexService.findMenu(menu);
			//System.out.println("..........menuId:"+menuId);
			//if (menuId != null){
				result.put("status", 1);
				Integer userType = userService.userType(Long.parseLong(request.getSession().getAttribute("userId").toString()));
				result.put("secondMenu", indexService.loadSecondMenu(menu, userType));
			//}else {


		}
		return result;
	}
	@RequestMapping("/saveDiary.do")
	@ResponseBody
	public HashMap<String, Object> saveDiary(HttpServletRequest request, Long diaryModuleId
			,String title,String content, String memo){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(request.getSession().getAttribute("userId") == null){
			result.put("status", CommonStatus.GoToLogin.getValue());
		}else{
			Long user = Long.parseLong(request.getSession().getAttribute("userId").toString());
			
			List<net.sf.json.JSONObject> data = JsonAnalysis.analysis(content);
			// momo 功能保留
			/*String memo = "";
			for (int i = 0;i<data.size();i++){
				net.sf.json.JSONObject d = data.get(i);
				memo += "【"+data.get(i).getString("title")+"】:"+data.get(i).getString("content");
				if (i < data.size() -1){
					
				}
			}*/
			Long logId = logService.save(title, memo, user, diaryModuleId, userDao.userCompany(user));
			List<TemplateTag> tags = templateTagService.loadModuleById(diaryModuleId);
			List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
			for (int i = 0;i<tags.size();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user", user);
				map.put("log", logId);
				map.put("tag", tags.get(i).getId());
				if (tags.get(i).getType() == 1 || tags.get(i).getType() == 2){
					if (data.get(i).get("content").toString().indexOf(",") != -1){
						String [] val = data.get(i).get("content").toString().split(",");
						String res = "";
						String [] opt = tags.get(i).getContent().split("-=-");
						for(String va : val){
							Integer v = Integer.parseInt(va);
							res += opt[v]+"-=-";
						}
						map.put("content", res.substring(0, res.length()-3));
					}else {
						Integer val = Integer.parseInt(data.get(i).get("content").toString());
						String [] opt = tags.get(i).getContent().split("-=-");
						
						map.put("content", opt[val]);
					}
					
				}else{
					map.put("content", data.get(i).get("content"));
				}

				map.put("template", diaryModuleId);
				lists.add(map);
			}
			if(lists.size() > 0){
				logContentService.save(lists);
			}
			result.put("status", CommonStatus.Normal.getValue());
		}
		return result;
	}
	/**
	 * 
	 * @param id 日志Id
	 * @param userId 用户Id
	 * @return
	 */
	@RequestMapping("/delDiary.do")
	@ResponseBody
	public HashMap<String, Object> delDiary(Long id, Integer status, HttpServletRequest request, 
			HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if(id != null){
			Log log = logDao.loadModuleById(id);
			if(status == null){
				log.setStatus(CommonStatus.Delete.getValue());
				logDao.delDiary(log);
			}else{
				if(status == 1) log.setStatus(LogStatus.UnDo.getValue());
				if(status == 2) log.setStatus(LogStatus.Refused.getValue());
				
				logDao.delDiary(log);
			}
			//当前作者删除
			/*
			if(request.getSession().getAttribute("userId") != null){
				if(status == null) {
					Long currUserId = Long.parseLong(request.getSession().getAttribute("userId").toString());
					if(currUserId.equals(log.getUser())){
						log.setStatus(CommonStatus.Delete.getValue());
						logDao.delDiary(log);
					}else{
						//公司管理员删除
						User user = userDao.loadUserById(currUserId);
						if(user.getCompany().equals(log.getCompany()) 
								&& user.getUser_type() == UserType.CompanyManager.getValue()
								){
							log.setStatus(CommonStatus.Delete.getValue());
							logDao.delDiary(log);
						}else if(user.getUser_type() == UserType.Admin.getValue()){
							log.setStatus(CommonStatus.Delete.getValue());
							logDao.delDiary(log);
						}
					}
				}else{
					
					if(status == 1) status = LogStatus.UnDo.getValue();
					if(status == 2) status = LogStatus.Refused.getValue();
					log.setStatus(status);
					logDao.delDiary(log);
				}
			}*/
			
		}
		result.put("msg", "操作成功！");
		return result;
	}
	@RequestMapping("/delLogModule.do")
	@ResponseBody
	public HashMap<String, Object> delLogModule(Long id, HttpServletRequest request, 
			HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		int status = 2;
		if(id != null){
			if(request.getSession().getAttribute("userId") != null){
				Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
				User user = userDao.loadUserById(userId);
				if(user.getUser_type() == UserType.CompanyManager.getValue()){
					LogTemplate logTemplate = logTemplateDao.loadLogTemplateById(id);
					logTemplate.setStatus(CommonStatus.Delete.getValue());
					logTemplateDao.delLogModule(logTemplate);
					status = CommonStatus.Normal.getValue();
				}
			}
		}
		result.put("status", status);
		return result;
	}
	@RequestMapping("/diaryLike.do")
	@ResponseBody
	public HashMap<String, Object> diaryLike(Long id, HttpServletRequest request, 
			HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			if(logLikeDao.checkLogLike(id, userId) == 0){
				LogLike like = new LogLike();
				like.setCreate_time(new Timestamp(System.currentTimeMillis()));
				like.setLog(id);
				like.setUser(userId);
				like.setStatus(CommonStatus.Normal.getValue());
				logLikeDao.saveLogLike(like);
				result.put("msg", "点赞成功！");
			}else {
				result.put("msg", "您已经点赞过了");
			}

		}else{
			result.put("msg", "登录信息过期");
		}
	
		return result;
	}
	@RequestMapping("/getDept.do")
	@ResponseBody
	public void getDept(HttpServletRequest request, HttpServletResponse response){
		try{
			
			request.setCharacterEncoding("UTF-8");
			String data = request.getParameter("a"); 
			byte[] source = data.getBytes("ISO8859-1");
			data = new String(source, "UTF-8"); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
