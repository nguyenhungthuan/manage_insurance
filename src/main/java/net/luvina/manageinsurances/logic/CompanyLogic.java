/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *CompanyLogic.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic;

import java.util.List;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;

/**
 * Interface CompanyLogic
 * @author DELL
 *
 */
public interface CompanyLogic {
	
	/**
	 * Get all company
	 * @return list company
	 */
	public List<CompanyDto> getAllCom();
	
	/**
	 * Get company by id
	 * @param id id
	 * @return companyDto
	 */
	public CompanyDto getCompanyByID(int id);
	
	/**
	 * Check exist company
	 * @param id id
	 * @return boolean
	 */
	public Boolean checkExistCompany(int id);
	
	/**
	 * Check exist email
	 * @param userID userInternalID
	 * @return boolean
	 */
	public Boolean checkExistEmail(String email);
	
	/**
	 * Check exist tel
	 * @param tel telephone
	 * @return boolean
	 */
	public Boolean checkExistTel(String tel);
}