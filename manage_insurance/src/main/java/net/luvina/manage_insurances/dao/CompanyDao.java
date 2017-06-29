/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *CompanyDao.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.dao;

import java.util.List;

import net.luvina.manageinsurances.entities.CompanyBean;

/**
 * Interface CompanyDao
 * @author DELL
 *
 */
public interface CompanyDao {
	
	/**
	 * Phương thức lấy tất cả các công ty trong DB
	 * @return list chứa toàn bộ công ty
	 */
	public List<CompanyBean> getAllCom();
	
	/**
	 * Phương thức lấy ra thông tin công ty theo id
	 * @param id id
	 * @return tblcompany
	 */
	public CompanyBean getCompanyByID(int id);
	
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
