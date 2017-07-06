/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserLogicImpl.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.logic.impl.dto.AccountDto;
import net.luvina.manageinsurances.logic.UserLogic;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.utils.Common;

/**
 * Class UserLogicImpl
 * @author DELL
 *
 */

@Component
public class UserLogicImpl implements UserLogic {
	@Autowired
	private UserDao userDao;
	@Autowired
	private CompanyDao companyDao;
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#checkExistedAcc(java.lang.String, java.lang.String)
	 */
	public boolean checkExistedAcc(String userName, String password){
		return userDao.findByUserNameAndPassword(userName, password).size() > 0 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#getListInfor(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<UserInsuranceDto> getListInfor(InforSearchDto inforSearchDto, String sortBy, int limit, int offset) {
		String fullName = Common.processWildcard(inforSearchDto.getFullName());
		String insuranceNumber = Common.processWildcard(inforSearchDto.getInsuranceNumber());
		String placeOfRegister = Common.processWildcard(inforSearchDto.getPlaceOfRegister());
		int comID = Integer.parseInt(inforSearchDto.getCompanyInternalID());
		return Common.copyProListDtoToBean(userDao.getListInfor(comID, fullName, insuranceNumber, placeOfRegister, inforSearchDto.getSortType(), sortBy, limit, offset));
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#getTotalRecords(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int getTotalRecords(InforSearchDto inforSearchDto) {
		String fullName = Common.processWildcard(inforSearchDto.getFullName());
		String insuranceNumber = Common.processWildcard(inforSearchDto.getInsuranceNumber());
		String placeOfRegister = Common.processWildcard(inforSearchDto.getPlaceOfRegister());
		int comID = Integer.parseInt(inforSearchDto.getCompanyInternalID());
		return userDao.getTotalRecords(comID, fullName, insuranceNumber, placeOfRegister);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#checkExistUser(int)
	 */
	public Boolean checkExistUser(int userInternalID) {
		return userDao.findByUserInternalID(userInternalID) != null ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#getDetailsInfor(int)
	 */
	public UserInsuranceDto getDetailsInfor(int userInternalID) {
		UserInsuranceBean userInsuranceBean = userDao.getDetailsInfor(userInternalID);
		return Common.copyPropertyUIBeanToUIDto(userInsuranceBean);
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#checkExistedInsuNum(java.lang.String, int)
	 */
	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId) {
		return userDao.checkExistedInsuNum(insuranceNumber, userInternalId);
	}
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#insertOrUpdateUser(net.luvina.manageinsurances.entities.UserInsuranceFormBean, net.luvina.manageinsurances.entities.AccountFormBean)
	 */
	public Boolean insertOrUpdateUser(UserInsuranceDto userInsuranceDto, AccountDto accountDto) {
		try{
			CompanyBean company = new CompanyBean(userInsuranceDto.getCompanyInternalID());
			if(companyDao.findByCompanyInternalId(userInsuranceDto.getCompanyInternalID()) == null) {
				company = new CompanyBean(userInsuranceDto.getCompanyInternalID(), userInsuranceDto.getCompanyName(), userInsuranceDto.getCompanyAddress(), userInsuranceDto.getEmail(), userInsuranceDto.getTelephone());
			}
			InsuranceBean insurance = new InsuranceBean(userDao.getInsuranceInternalID(userInsuranceDto.getUserInternalID()), userInsuranceDto.getInsuranceNumber(), Common.convertDateHQL(userInsuranceDto.getInsuranceStartDate()), Common.convertDateHQL(userInsuranceDto.getInsuranceEndDate()), userInsuranceDto.getPlaceOfRegister());
			UserBean user = new UserBean(userInsuranceDto.getUserInternalID(), Common.convertStringName(userInsuranceDto.getFullName()), String.valueOf(userInsuranceDto.getSex()), Common.convertDateHQL(userInsuranceDto.getBirthday()), company, insurance);
			user.setUserName(accountDto.getUserName());
			user.setPassword(accountDto.getPassword());
			return userDao.insertOrUpdateUser(user, insurance, company);
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}