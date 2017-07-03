/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserLogicImpl.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.logic.impl.dto.AccountDto;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;
import net.luvina.manageinsurances.logic.UserLogic;
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
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.UserLogic#checkExistedAcc(java.lang.String, java.lang.String)
	 */
	public boolean checkExistedAcc(String userName, String password){
		if(userDao.findByUserNameAndPassword(userName, password).size() > 0) {
			return true;
		}
		return false;
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
}
