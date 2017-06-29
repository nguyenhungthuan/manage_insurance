/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *CompanyLogicImpl.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;
import net.luvina.manageinsurances.logic.CompanyLogic;
import net.luvina.manageinsurances.utils.Common;

/**
 * Class CompanyLogicImpl
 * @author DELL
 *
 */
@Component
public class CompanyLogicImpl implements CompanyLogic{
	@Autowired
	private CompanyDao companyDao;
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#getAllCom()
	 */
	public List<CompanyDto> getAllCom() {
		return Common.castListCom(companyDao.getAllCom());
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#getCompanyByID(int)
	 */
	public CompanyDto getCompanyByID(int id) {
		return Common.copyProCom(companyDao.getCompanyByID(id));
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#checkExistedCom(int)
	 */
	public Boolean checkExistedCom(int id) {
		return companyDao.checkExistedCom(id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#checkExistedEmail(java.lang.String)
	 */
	public Boolean checkExistedEmail(String email) {
		return companyDao.checkExistedEmail(email);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#checkExistedTel(java.lang.String)
	 */
	public Boolean checkExistedTel(String tel) {
		return companyDao.checkExistedTel(tel);
	}
	
}
