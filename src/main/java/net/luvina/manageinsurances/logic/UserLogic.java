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
}
