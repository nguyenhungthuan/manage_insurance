/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserLogicImpl.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.dao.InsuranceDao;
import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.entities.UserBean;
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
	@Autowired
	private InsuranceDao insuranceDao;
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#checkExistedAcc(java.lang.String, java.lang.String)
	 */
	public boolean checkExistAccount(String userName, String password){
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
		try {
			return Common.copyProListDtoToBean(userDao.getListInfor(comID, fullName, insuranceNumber, placeOfRegister, inforSearchDto.getSortType(), sortBy, limit, offset));
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			return new ArrayList<UserInsuranceDto>();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#getTotalRecords(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int getTotalRecords(InforSearchDto inforSearchDto) {
		int comID = Integer.parseInt(inforSearchDto.getCompanyInternalID());
		return userDao.getTotalRecords(comID, Common.processWildcard(inforSearchDto.getFullName()), Common.processWildcard(inforSearchDto.getInsuranceNumber()), Common.processWildcard(inforSearchDto.getPlaceOfRegister()));
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
		UserBean userBean = userDao.findByUserInternalID(userInternalID);
		UserInsuranceDto userInsuranceDto = Common.combineUBAndUIBToUID(userBean);
		userInsuranceDto.setCompanyName(userBean.getCompany().getCompanyName());
		userInsuranceDto.setSex(Common.sexByString(Integer.parseInt(userBean.getSex())));
		return userInsuranceDto;
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
	@Transactional
	public Boolean insertOrUpdateUser(UserInsuranceDto userInsuranceDto, AccountDto accountDto) {
		CompanyBean company = setAttributeForCompany(userInsuranceDto);
		InsuranceBean insurance = setAttributeForInsurance(userInsuranceDto); 
		UserBean user = setAttributeForUser(userInsuranceDto, company, insurance, accountDto);
		if (company.getCompanyInternalId() == 0) {
			companyDao.save(company);
		}
		insuranceDao.save(insurance);
		userDao.save(user);
		return true;
	}

	private CompanyBean setAttributeForCompany(UserInsuranceDto userInsurance) {
		CompanyBean company = new CompanyBean(userInsurance.getCompanyInternalID());
		if (companyDao.findByCompanyInternalId(userInsurance.getCompanyInternalID()) == null) {
			company.setCompanyInternalId(userInsurance.getCompanyInternalID());
			company.setCompanyName(userInsurance.getCompanyName());
			company.setAddress(userInsurance.getCompanyAddress());
			company.setEmail(userInsurance.getEmail());
			company.setTel(userInsurance.getTelephone());
		}
		return company;
	}
	
	private InsuranceBean setAttributeForInsurance(UserInsuranceDto userInsurance) {
		InsuranceBean insurance = new InsuranceBean();
		insurance.setInsuranceInternalId(userDao.getInsuranceInternalID(userInsurance.getUserInternalID()));
		insurance.setInsuranceNumber(userInsurance.getInsuranceNumber());
		insurance.setInsuranceStartDate(Common.convertDateHQL(userInsurance.getInsuranceStartDate()));
		insurance.setInsuranceEndDate(Common.convertDateHQL(userInsurance.getInsuranceEndDate()));
		insurance.setPlaceOfRegister(userInsurance.getPlaceOfRegister());
		return insurance;
	}
	
	private UserBean setAttributeForUser(UserInsuranceDto userInsurance, CompanyBean company, InsuranceBean insurance, AccountDto account) {
		UserBean user = new UserBean();
		user.setUserName(account.getUserName());
		user.setPassword(account.getPassword());
		user.setUserInternalID(userInsurance.getUserInternalID());
		user.setFullName(Common.convertName(userInsurance.getFullName()));
		user.setSex(String.valueOf(userInsurance.getSex()));
		user.setBirthday(Common.convertDateHQL(userInsurance.getBirthday()));
		user.setCompany(company);
		user.setInsurance(insurance);
		return user;
	}
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#deleteUser(int)
	 */
	@Transactional
	public Boolean deleteUser(int userInternalID) {
		UserBean userBean = userDao.findByUserInternalID(userInternalID);
		if(userBean != null) {
			InsuranceBean insuranceBean = userBean.getInsurance();
			insuranceDao.delete(insuranceBean);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#getUserById(int)
	 */
	public UserInsuranceDto getUserById(int userId) {
		return Common.combineUBAndUIBToUID(userDao.findByUserInternalID(userId));
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#getListDataToExport(net.luvina.manageinsurances.logic.impl.dto.InforSearchDto, java.lang.String)
	 */
	public ScrollableResults getListDataToExport(InforSearchDto inforSearchDto, String sortBy) {
		int comID = Integer.parseInt(inforSearchDto.getCompanyInternalID());
		String sortType = Common.checkSortType(inforSearchDto.getSortType());
		return userDao.getListDataToExport(comID, inforSearchDto.getFullName(), inforSearchDto.getInsuranceNumber(), inforSearchDto.getPlaceOfRegister(), sortType,sortBy);
	}
}