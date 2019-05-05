package QuanDiary.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.dao.IndexDao;
import QuanDiary.dao.LogContentDao;
import QuanDiary.dao.LogDao;
import QuanDiary.dao.LogTemplateDao;
import QuanDiary.dao.TemplateTagDao;
import QuanDiary.entity.Log;
import QuanDiary.entity.LogContent;
import QuanDiary.entity.LogTemplate;
import QuanDiary.entity.Menu;
import QuanDiary.entity.SecondMenu;
import QuanDiary.entity.TemplateTag;

@Service("logService")
public class LogService {
	@Resource
	LogDao logDao;
	@Resource
	LogContentDao logContentDao;
	@Resource
	LogTemplateDao logTemplateDao;
	@Resource
	TemplateTagDao tagDao;
	/*public List<LogTemplate> loadLogModuleData(Long companyId){
		if (companyId != null){
			List<LogTemplate> list = logDao.loadLogModule(companyId);
			return list;
		}
		
		return null;
		
	}*/
	public Long save(String title,String memo,Long user, Long template, Long company){
		Log log = new Log();
		log.setTitle(title);
		log.setMemo(memo);
		log.setStatus(CommonStatus.Normal.getValue());
		log.setUser(user);
		log.setTemplate(template);
		log.setCompany(company);
		log.setCreate_time(new Timestamp(System.currentTimeMillis()));
		logDao.save(log);
		return log.getId();
	}

	public Integer countByLoadMyLog(Long user, Integer status){
		return logDao.countByLoadMyLog(user, status);
	}
	public Log loadModuleById(Long id){
		return logDao.loadModuleById(id);
	}
	public HSSFWorkbook getHSSFWorkbook(HSSFWorkbook wb, Long moduleId, String sheetName,
			List<Long> logIds, HashMap<Long, String> userNameByLogId){
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		if(wb == null){
            wb = new HSSFWorkbook();
        }
		HSSFSheet sheet;
		if(wb.getSheet(sheetName) == null){
			sheet = wb.createSheet(sheetName);
		}else{
			sheet = wb.getSheet(sheetName);
		}

        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
     // 生成一个字体
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        //font.setColor(HSSFColor.RED.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("黑体");
        style.setFont(font);
        //声明列对象
        HSSFCell cell = null;
        //创建标题
        List<TemplateTag> tags = tagDao.loadModuleById(moduleId);
        //名字
        cell = row.createCell(0);
        cell.setCellValue("作者");
    	cell.setCellStyle(style);
    	cell = null;
        for(int i = 0;i < tags.size();i++){
        	cell = row.createCell(i+1);
        	cell.setCellValue(tags.get(i).getTitle());
        	cell.setCellStyle(style);
        }
        //创建内容
        for(int i = 0;i < logIds.size();i++){
        	row = sheet.createRow(i + 1);
        	row.createCell(0).setCellValue(userNameByLogId.get(logIds.get(i)));
        	
        	List<LogContent> contents = logContentDao.viewDiary(logIds.get(i));
        	for(int j = 0;j < contents.size();j++){
        		
        		if(tags.get(j).getType() == 0 ||
        				tags.get(j).getType() == 1 ||
        				tags.get(j).getType() == 3){
        			
        			row.createCell(j+1).setCellValue(contents.get(j).getContent());
        		}else if(tags.get(j).getType() == 2){
        			String [] val = contents.get(j).getContent().split("-=-");
        			String value = "";
        			for(int k = 0 ;k<val.length;k++){
        				value += val[k];
        				if(k != val.length-1) value += "、";
        			}
        			row.createCell(j+1).setCellValue(value);
        		}else if(tags.get(j).getType() == 4){
        			
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        			String time = sdf.format(new Date(Long.parseLong(contents.get(j).getContent())));
        			row.createCell(j+1).setCellValue(time);
        		}else if(tags.get(j).getType() == 5){
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        			String time = sdf.format(new Date(Long.parseLong(contents.get(j).getContent())));
        			row.createCell(j+1).setCellValue(time);
        			
        		}
        		
        	}
        }
        /*
       
        
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
       
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        
        */
        return wb;
    }
}
