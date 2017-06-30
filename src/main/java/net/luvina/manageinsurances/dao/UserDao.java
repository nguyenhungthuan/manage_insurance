/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserDao.java, Dec 3, 2016 [Nguyá»…n HÆ°ng Thuáº­n]
 */
package net.luvina.manageinsurances.dao;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ScrollableResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.controller.formbean.UserInsuranceFormBean;

/**
 * Interface UserDao
 * @author DELL
 *
 */
@Repository
public interface UserDao extends JpaRepository<UserBean, Integer> {
	
	
	/**
	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra tá»“n táº¡i user
	 * @param userName user name
	 * @param password pass
	 * @return true, false
	 */
	public List<UserBean> findByUserNameAndPassword(String userName, String password);
	
//	/**
//	 * PhÆ°Æ¡ng thá»©c láº¥y ra 1 list User theo Ä‘iá»�u  kiá»‡n tÃ¬m kiáº¿m
//	 * @param company companyID
//	 * @param fullName fullName
//	 * @param code insuranceNumber
//	 * @param place placeOfRegister
//	 * @param sortBy sortBy
//	 * @param sortType sortType
//	 * @param limit limit
//	 * @param offset offset
//	 * @return list UserInsuranceFormBean
//	 */
//	public List<UserInsuranceBean> getListInfor(int company, String fullName, String code, String place, String sortBy, String sortType, int limit, int offset);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c láº¥y tá»•ng sá»‘ user theo Ä‘iá»�u kiá»‡n tÃ¬m kiáº¿m
//	 * @param companyID companyID
//	 * @param fullName full name search
//	 * @param insuranceNumber insurance number search
//	 * @param registerPlace register place search
//	 * @return total users
//	 */
//	public int getTotalRecords(int companyID, String fullName, String insuranceNumber, String registerPlace);
//	
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra tá»“n táº¡i user trong DB
//	 * @param userId user id
//	 * @return true náº¿u cÃ³ tá»“n táº¡i vÃ  ngÆ°á»£c láº¡i
//	 */
//	public Boolean checkExistedUser(int userId);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c xÃ³a má»™t user
//	 * @param userID user id
//	 * @return true náº¿u xÃ³a thÃ nh cÃ´ng vÃ  ngÆ°á»£c láº¡i
//	 */
//	public Boolean deleteUser(int userID);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c generic láº¥y ra thÃ´ng tin user theo id
//	 * @param userId id
//	 * @return object
//	 */
//	public UserInsuranceBean getUserById(int userId);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c insert, update thÃ´ng tin user
//	 * @param user user
//	 * @param insurance insurance
//	 * @param company company
//	 * @return
//	 * @throws Exception 
//	 */
//	public Boolean insertOrUpdateUser(UserBean user, InsuranceBean insurance, CompanyBean company) throws Exception;
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra tá»“n táº¡i mÃ£ sá»‘ tháº» báº£o hiá»ƒm
//	 * @param insuranceNumber mÃ£ sá»‘ tháº» báº£o hiá»ƒm
//	 * @param userInternalId id user
//	 * @return true náº¿u Ä‘Ã£ tá»“n táº¡i vÃ  ngÆ°á»£c láº¡i
//	 */
//	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c láº¥y dá»¯ liá»‡u theo Ä‘iá»�u kiá»‡n tÃ¬m kiáº¿m Ä‘á»ƒ export csv
//	 * @param companyID
//	 * @param fullName
//	 * @param insuranceNumber
//	 * @param registerPlace
//	 * @param sortType
//	 * @param sortBy
//	 * @return
//	 */
//	public ScrollableResults getListDataToExport(int companyID, String fullName, String insuranceNumber, String registerPlace, String sortType, String sortBy);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c láº¥y thÃ´ng tin chi tiáº¿t hiá»ƒn thá»‹ MH03
//	 * @param userID userInternalID
//	 * @return UserInsuranceBean
//	 */
//	public UserInsuranceBean getDetailsInfor(int userID);
//	
//	/**
//	 * PhÆ°Æ¡ng thá»©c láº¥y ra insuranceInternalID theo userInternalID
//	 * @param userID userInternalID
//	 * @return insuranceInternalID
//	 */
//	public int getInsuranceInternalID(int userID);
//
}
