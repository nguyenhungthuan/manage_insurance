/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *CompanyLogic.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.logic;

import java.util.List;

import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.logic.impl.dto.CompanyDto;

/**
 * Interface CompanyLogic
 * @author DELL
 *
 */
public interface CompanyLogic {
	
	/**
	 * Phương thức lấy tất cả các công ty trong DB
	 * @return list chứa toàn bộ công ty
	 */
	public List<CompanyDto> getAllCom();
	
	/**
	 * Lấy thông tin của công ty theo id 
	 * @param id id
	 * @return companyDto
	 */
	public CompanyDto getCompanyByID(int id);
	
	/**
	 * Phương thức kiểm tra tồn tại công ty trong DB
	 * @param id id
	 * @return true nếu có và ngược lại
	 */
	public Boolean checkExistedCom(int id);
	
	/**
	 * Phương thức kiểm tra đã tồn tại email trong DB chưa
	 * @param userID userInternalID
	 * @return true nếu có tồn tại và ngược lại
	 */
	public Boolean checkExistedEmail(String email);
	
	/**
	 * Phương thức kiểm tra tồn tại số điện thoại trong DB
	 * @param tel số điện thoại
	 * @return return true nếu tồn tại và ngược lại
	 */
	public Boolean checkExistedTel(String tel);
}
