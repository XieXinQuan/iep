package QuanDiary.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import QuanDiary.Util.enumUtil.CommonStatus;
import QuanDiary.Util.enumUtil.UserStatus;
import QuanDiary.dao.ApprovalDao;
import QuanDiary.dao.UserDao;
import QuanDiary.entity.Approval;
import QuanDiary.entity.User;

@Component
public class timer {
	
	@Resource
	UserDao userDao;
	@Resource
	ApprovalDao approvalDao;
	/**
	 * 定时器 -- 每一分钟执行一次  --更改员工请假/出差状态
	 */
	@Scheduled(cron="0 */1 * * * ?")
	public void timer(){
		List<Integer> status = new ArrayList<Integer>();
		status.add(UserStatus.AskForLeave.getValue());
		status.add(UserStatus.BusinessTrip.getValue());
		
		List<User> users = userDao.loadAllAskOfLeaveUser(status);
		for(User user : users){
			Long time = approvalDao.loadApprovalByTimer(user.getStatus(), user.getId(), UserStatus.Agree.getValue());
			if(time != null && time*1000 < System.currentTimeMillis()){
				user.setStatus(CommonStatus.Normal.getValue());
				userDao.updateUserStatus(user);
			}
		}
		
	}
}
