/**
 * 
 */
package net.luvina.manageinsurances.dao.custom;

import java.util.List;

import net.luvina.manageinsurances.entities.UserInsuranceBean;

/**
 * @author nguyenhungthuan
 *
 */
public interface UserDaoCustom {
	/**
	 * Phương thức lấy ra 1 list User theo điều  kiện tìm kiếm
	 * @param company companyID
	 * @param fullName fullName
	 * @param code insuranceNumber
	 * @param place placeOfRegister
	 * @param sortBy sortBy
	 * @param sortType sortType
	 * @param limit limit
	 * @param offset offset
	 * @return list UserInsuranceFormBean
	 */
	public List<UserInsuranceBean> getListInfor(int company, String fullName, String code, String place, String sortBy, String sortType, int limit, int offset);
	
	/**
	 * Phương thức lấy tổng số user theo điều kiện tìm kiếm
	 * @param companyID companyID
	 * @param fullName full name search
	 * @param insuranceNumber insurance number search
	 * @param registerPlace register place search
	 * @return total users
	 */
	public int getTotalRecords(int companyID, String fullName, String insuranceNumber, String registerPlace);
}
