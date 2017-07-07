/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserLogic.java, Dec 4, 2016 [Nguyá»…n HÆ°ng Thuáº­n]
 */
package net.luvina.manageinsurances.logic;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ScrollableResults;

import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
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
	 * Phương thức kiểm tra tồn tại account 
	 * @param userName user name
	 * @param password pass
	 * @return true, false
	 */
	public boolean checkExistedAcc(String userName, String password);
	
	/**
	 * Phương thức lấy ra danh sách người dùng theo tiêu chí tìm kiếm
	 * @param inforSearchDto InforSearchDto
	 * @param sortType sortType
	 * @param limit limit
	 * @param offset offset
	 * @return list UserInsuranceFormBean
	 */
	public List<UserInsuranceDto> getListInfor(InforSearchDto inforSearchDto, String sortType, int limit, int offset);
	
	/**
	 * Phương thức lấy ra số kết quả phù hợp
	 * @param companyID companyID
	 * @param inforSearchDto InforSearchDto
	 * @return total users
	 */
	public int getTotalRecords(InforSearchDto inforSearchDto);
	
	/**
	 * Phương thức kiểm tra tồn tại user trong DB
	 * @param userId user id
	 * @return true nếu có tồn tại và ngược lại
	 */
	public Boolean checkExistUser(int userId);
	
	/**
	 * Phương thức lấy thông tin chi tiết hiển thị MH03
	 * @param userID userInternalID
	 * @return UserInsuranceBean
	 */
	public UserInsuranceDto getDetailsInfor(int userID);
	
	/**
	 * Phương thức kiểm tra tồn tại mã số thẻ bảo hiểm
	 * @param insuranceNumber mã số thẻ bảo hiểm
	 * @param userInternalId id user
	 * @return true nếu đã tồn tại và ngược lại
	 */
	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId);
	
	/**
	 * Phương thức insert, update 1 user
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
	 * Phương thức generic lấy ra thông tin user theo id
	 * @param userId id
	 * @return object
	 */
	public UserInsuranceDto getUserById(int userId);
	
	/**
	 * Phương thức lấy dữ liệu theo điều kiện tìm kiếm để export csv
	 * @param inforSearchDto InforSearchDto
	 * @param sortBy sortBy
	 * @return ScrollableResults
	 */
	public ScrollableResults getListDataToExport(InforSearchDto inforSearchDto, String sortBy);
}
