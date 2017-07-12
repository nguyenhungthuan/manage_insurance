/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserLogic.java, Dec 4, 2016 [Nguyá»…n HÆ°ng Thuáº­n]
 */
package net.luvina.manageinsurances.logic;

import java.util.List;
import org.hibernate.ScrollableResults;
import net.luvina.manageinsurances.logic.impl.dto.AccountDto;
import net.luvina.manageinsurances.logic.impl.dto.InforSearchDto;
import net.luvina.manageinsurances.logic.impl.dto.UserInsuranceDto;

/**
 * Interface UserLogic
 * @author DELL
 *
 */
public interface UserLogic {
	
	/**
	 * Check exist account 
	 * @param userName user name
	 * @param password pass
	 * @return true, false
	 */
	public boolean checkExistAccount(String userName, String password);
	
	/**
	 * Get list user
	 * @param inforSearchDto InforSearchDto
	 * @param sortType sortType
	 * @param limit limit
	 * @param offset offset
	 * @return list UserInsuranceFormBean
	 */
	public List<UserInsuranceDto> getListInfor(InforSearchDto inforSearchDto, String sortType, int limit, int offset);
	
	/**
	 * Get total records
	 * @param companyID companyID
	 * @param inforSearchDto InforSearchDto
	 * @return total users
	 */
	public int getTotalRecords(InforSearchDto inforSearchDto);
	
	/**
	 * Check exist user
	 * @param userId user id
	 * @return true nếu có tồn tại và ngược lại
	 */
	public Boolean checkExistUser(int userId);
	
	/**
	 * Get details information
	 * @param userID userInternalID
	 * @return UserInsuranceBean
	 */
	public UserInsuranceDto getDetailsInfor(int userID);
	
	/**
	 * Check exist insurance number
	 * @param insuranceNumber mã số thẻ bảo hiểm
	 * @param userInternalId id user
	 * @return true nếu đã tồn tại và ngược lại
	 */
	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId);
	
	/**
	 * Insert, update 1 user
	 * @param userInsuranceDto user
	 * @param accountDto account chứa username và password đã đăng nhập
	 * @return true nếu thêm thành công và ngược lại
	 */
	public Boolean insertOrUpdateUser(UserInsuranceDto userInsuranceDto, AccountDto accountDto);
	
	/**
	 * Method delete user
	 * @param userID userInternalID
	 * @return true if complete
	 */
	public Boolean deleteUser(int userID);
	
	/**
	 * Get user by id
	 * @param userId id
	 * @return object
	 */
	public UserInsuranceDto getUserById(int userId);
	
	/**
	 * Get list data to export
	 * @param inforSearchDto InforSearchDto
	 * @param sortBy sortBy
	 * @return ScrollableResults
	 */
	public ScrollableResults getListDataToExport(InforSearchDto inforSearchDto, String sortBy);
}
