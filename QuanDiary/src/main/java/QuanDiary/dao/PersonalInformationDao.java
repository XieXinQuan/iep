package QuanDiary.dao;

import QuanDiary.entity.PersonalInformation;

public interface PersonalInformationDao {
	public void save(PersonalInformation personalInformation);
	public PersonalInformation loadPersonalInformation(Long userId);
	public void updatePersonInfor(PersonalInformation pi);
}
