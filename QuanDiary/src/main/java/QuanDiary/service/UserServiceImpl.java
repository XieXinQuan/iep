package QuanDiary.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.Util.md5Util.Md5;
import QuanDiary.dao.DeptDao;
import QuanDiary.dao.UserDao;
import QuanDiary.entity.Company;
import QuanDiary.entity.Dept;
import QuanDiary.entity.User;

@Service("userService")
public class UserServiceImpl{

	@Resource
	UserDao userDao;
	@Resource
	DeptDao deptDao;
	public Long getCurrUser(HttpServletRequest request){
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			return userId;
		}
		return 0l;
	}
	public Boolean checkName(String loginName) {
		Integer count = userDao.checkName(loginName);
		Boolean result = count == 0 ? true : false;
		return result;
	}
	public Boolean save(String loginName, String password, String displayName) {

		if (Md5.md5(password) != null){
			User user = new User();
			user.setStatus(new Integer(CommonStatus.Normal.getValue()));
			user.setLogin_name(loginName);
			user.setPassword(Md5.md5(password));
			user.setDisplay_name(displayName);
			userDao.save(user);
			return true;
		}
		return false;


	}
	public HashMap<String, String> login(String loginName, String password) {
		HashMap<String, String> result = new HashMap<String, String>();
		
		User user = userDao.login(loginName);
		if (user == null) {
			result.put("msg", "用户不存在！");
		}else {
			if (!Md5.md5(password).equals(user.getPassword())){
				result.put("msg", "密码错误");
			}else {
				result.put("userId", ""+user.getId());
				result.put("msg", "登录成功！");
			}
		}
		return result;
	}
	public Integer userType(Long id) {
		Integer userType = userDao.userType(id);
		return userType;
	}
	public Long userCompany(Long userId) {
		return userDao.userCompany(userId);
	}
	public List<Map<String, Object>> testMap() {
		
		return userDao.testMap();
	}
	public List<Map<String, Object>> testParameters(String loginName, Integer status) {
		// TODO Auto-generated method stub
		return userDao.testParameters(loginName, status);
	}
	public List<Map<String, Object>> testParameters1(String loginName, Integer status) {
		// TODO Auto-generated method stub
		return userDao.testParameters1(loginName, status);
	}
	public String loadName(Long userId) {
		// TODO Auto-generated method stub
		return userDao.loadName(userId);
	}
	public void addEmployeeBatch(List<Map<String, Object>> users) {
		//userDao.addEmployeeBatch(users);
	}



	private final static String xls = "xls";  
	private final static String xlsx = "xlsx";  
	      
    /** 
     * 读入excel文件，解析后返回 
     * @param file 
     * @throws IOException  
     */  
	public static List<String[]> readExcel(MultipartFile file) throws IOException{  
        //检查文件  

        checkFile(file);  
        //获得Workbook工作薄对象  

        Workbook workbook = getWorkBook(file);  

        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回  
        List<String[]> list = new ArrayList<String[]>();  
        if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                //获得当前sheet工作表  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    continue;  
                }  
                //获得当前sheet的开始行  
                int firstRowNum  = sheet.getFirstRowNum();  
                //获得当前sheet的结束行  
                int lastRowNum = sheet.getLastRowNum();  
                //循环除了第一行的所有行  
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){  
                    //获得当前行  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        continue;  
                    }  
                    //获得当前行的开始列  
                    int firstCellNum = row.getFirstCellNum();  
                    //获得当前行的列数  
                    int lastCellNum = row.getPhysicalNumberOfCells();  
                    String[] cells = new String[row.getPhysicalNumberOfCells()];  
                    //循环当前行  
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
                        Cell cell = row.getCell(cellNum);  
                        cells[cellNum] = getCellValue(cell);  
                    }  
                    list.add(cells);  
                }  
            }  
            workbook.close();  
        }  
        return list;  
    }  
    public static void checkFile(MultipartFile file) throws IOException{  
        //判断文件是否存在  
        if(null == file){  
 
            throw new FileNotFoundException("文件不存在！");  
        }  
        //获得文件名  
        String fileName = file.getOriginalFilename();  
        //判断文件是否是excel文件  
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){  

            throw new IOException(fileName + "不是excel文件");  
        }  
    }  
    public static Workbook getWorkBook(MultipartFile file) {  
        //获得文件名  
        String fileName = file.getOriginalFilename();  

        //创建Workbook工作薄对象，表示整个excel  
        Workbook workbook = null;  
        try {  
            //获取excel文件的io流  
            InputStream is = file.getInputStream();  
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象  
            if(fileName.endsWith(xls)){  
                //2003  
                workbook = new HSSFWorkbook(is);  
            }else if(fileName.endsWith(xlsx)){  
                //2007  
                workbook = new XSSFWorkbook(is);  
            }  
        } catch (IOException e) {  
        	e.printStackTrace();
        }  
        return workbook;  
    }  
    public static String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
        //把数字当成String来读，避免出现1读成1.0的情况  
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
            cell.setCellType(Cell.CELL_TYPE_STRING);  
        }  
        //判断数据的类型  
        switch (cell.getCellType()){  
            case Cell.CELL_TYPE_NUMERIC: //数字  
                cellValue = String.valueOf(cell.getNumericCellValue());  
                break;  
            case Cell.CELL_TYPE_STRING: //字符串  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BLANK: //空值   
                cellValue = "";  
                break;  
            case Cell.CELL_TYPE_ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
	                break;  
	        }  
	        return cellValue;  
	    }  
	public String loadDeptData(String deptName,
			HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> result = new HashMap<String, Object>();
		JSONArray jsonArray = new JSONArray();

		
		//查找所有部门
		if(request.getSession().getAttribute("userId") != null){
			Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
			Long company = userDao.userCompany(userId);
			List<Dept> depts = deptDao.loadDept(company, CommonStatus.Normal.getValue());
			
			
			//公司名称
			Company company2 = userDao.loadCompanyByCompany(company);
			String comName;
			if(company2.getShort_name() != null){
				comName = company2.getShort_name();
			}else {
				comName = company2.getName();
			}
			//根节点
			jsonArray.add(putData(0, 0, comName, comName, true, true));
			//所有部门
			for(Dept dept : depts){
				//为避免tree显示userId 与 deptId 冲突，部门id设置为负值
				if(dept.getParent() != null){
					jsonArray.add(putData(-dept.getId(), -dept.getParent(), dept.getName(), dept.getName(), true, false));
				}else {
					//根节点id为o
					jsonArray.add(putData(-dept.getId(), 0, dept.getName(), dept.getName(), true, false));
				}
				List<User> users = userDao.loadUserByDept(dept.getId());
				for(User user : users){
					jsonArray.add(putData(user.getId(), -dept.getId(), user.getDisplay_name(), user.getDisplay_name(), false, false));
				}
			}
			//未分配部门id:-1 数据库中id=1
			jsonArray.add(putData(-1, 0, "未分配部门", "未分配部门", true, false));
			//未分配部门人
			List<User> users = userDao.loadUserByCompanyButDeptNull(company);
			for(User user : users){
				jsonArray.add(putData(user.getId(),-1,user.getDisplay_name(),user.getDisplay_name(),false,false));
			}
			result.put("status", CommonStatus.Normal.getValue());
			result.put("data", jsonArray.toString());
		}else {
			result.put("status", CommonStatus.GoToLogin.getValue());
		}
			


		return jsonArray.toString();
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
}


