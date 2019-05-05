package QuanDiary.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import QuanDiary.entity.Approval;
import QuanDiary.entity.Card;
import QuanDiary.entity.Company;
import QuanDiary.entity.User;

public interface ApprovalDao {
	
	public void updateApproval(Approval approval);
	public Approval viewApprovalById(Long id);
	public void saveApproval(Approval approval);
	public List<HashMap<String, Object>> loadCard(Long companyId,  String yearMonthDay, Integer page, Integer rows);
	public Integer checkUserCard(Long userId, String currDate);
	public void saveCard(Card card);
	
	public List<HashMap<String, Object>> loadApproval(Long companyId, Integer page, Integer rows);
	public Long loadApprovalByTimer(@Param("type") Integer type, @Param("user") Long user, 
			@Param("status") Integer status);
}
