/**
 * 
 */
package net.luvina.manageinsurances.dao.custom;

import java.util.List;

import org.hibernate.ScrollableResults;

import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;

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
	
	/**
	 * Phương thức kiểm tra tồn tại mã số thẻ bảo hiểm
	 * @param insuranceNumber mã số thẻ bảo hiểm
	 * @param userInternalId id user
	 * @return true nếu đã tồn tại và ngược lại
	 */
	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId);
	
	/**
	 * Phương thức lấy ra insuranceInternalID theo userInternalID
	 * @param userID userInternalID
	 * @return insuranceInternalID
	 */
	public int getInsuranceInternalID(int userID);
	
	/**
	 * Phương thức insert, update thông tin user
	 * @param user user
	 * @param insurance insurance
	 * @param company company
	 * @return
	 * @throws Exception 
	 */
	public Boolean insertOrUpdateUser(UserBean user, InsuranceBean insurance, CompanyBean company);

	/**
	 * Phương thức lấy dữ liệu theo điều kiện tìm kiếm để export csv
	 * @param companyID
	 * @param fullName
	 * @param insuranceNumber
	 * @param registerPlace
	 * @param sortType
	 * @param sortBy
	 * @return
	 */
	public ScrollableResults getListDataToExport(int companyID, String fullName, String insuranceNumber, String registerPlace, String sortType, String sortBy);
}