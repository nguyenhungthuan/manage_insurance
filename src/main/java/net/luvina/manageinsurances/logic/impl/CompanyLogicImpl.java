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
		return Common.castListCom(companyDao.findAll());
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#getCompanyByID(int)
	 */
	public CompanyDto getCompanyByID(int companyInternalId) {
		return Common.copyProCom(companyDao.findByCompanyInternalId(companyInternalId));
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#checkExistCompany(int)
	 */
	public Boolean checkExistCompany(int companyInternalId) {
		return companyDao.findByCompanyInternalId(companyInternalId) != null ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#checkExistEmail(java.lang.String)
	 */
	public Boolean checkExistEmail(String email) {
		return companyDao.findByEmail(email) != null ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.logic.CompanyLogic#checkExistTel(java.lang.String)
	 */
	public Boolean checkExistTel(String tel) {
		return companyDao.findByTel(tel) != null ? true : false;
	}
}