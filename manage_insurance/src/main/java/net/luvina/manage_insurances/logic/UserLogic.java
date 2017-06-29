/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserLogic.java, Dec 4, 2016 [Nguyá»…n HÆ°ng Thuáº­n]
 */
package net.luvina.manageinsurances.logic;

import java.util.List;
import java.util.stream.Stream;

import org.hibernate.ScrollableResults;

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
	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra tá»“n táº¡i account trong DB
	 * @param userName user name
	 * @param password pass
	 * @return true, false
	 */
	public Boolean checkExistedAcc(String userName, String password);
	
	/**
	 * PhÆ°Æ¡ng thá»©c láº¥y ra 1 list User theo Ä‘iá»�u  kiá»‡n tÃ¬m kiáº¿m
	 * @param inforSearchDto InforSearchDto
	 * @param sortType sortType
	 * @param limit limit
	 * @param offset offset
	 * @return list UserInsuranceFormBean
	 */
	public List<UserInsuranceDto> getListInfor(InforSearchDto inforSearchDto, String sortType, int limit, int offset);
	
	/**
	 * PhÆ°Æ¡ng thá»©c láº¥y tá»•ng sá»‘ user theo Ä‘iá»�u kiá»‡n tÃ¬m kiáº¿m
	 * @param companyID companyID
	 * @param inforSearchDto InforSearchDto
	 * @return total users
	 */
	public int getTotalRecords(InforSearchDto inforSearchDto);
	
	
	/**
	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra tá»“n táº¡i user trong DB
	 * @param userId user id
	 * @return true náº¿u cÃ³ tá»“n táº¡i vÃ  ngÆ°á»£c láº¡i
	 */
	public Boolean checkExistedUser(int userId);
	
	/**
	 * PhÆ°Æ¡ng thá»©c xÃ³a má»™t user
	 * @param userID user id
	 * @return true náº¿u xÃ³a thÃ nh cÃ´ng vÃ  ngÆ°á»£c láº¡i
	 */
	public Boolean deleteUser(int userID);
	
	/**
	 * PhÆ°Æ¡ng thá»©c generic láº¥y ra thÃ´ng tin user theo id
	 * @param userId id
	 * @return object
	 */
	public UserInsuranceDto getUserById(int userId);
	
	/**
	 * PhÆ°Æ¡ng thá»©c insert, update 1 user
	 * @param userInsuranceDto user
	 * @param accountDto account chá»©a username vÃ  password Ä‘Ã£ Ä‘Äƒng nháº­p
	 * @return true náº¿u thÃªm thÃ nh cÃ´ng vÃ  ngÆ°á»£c láº¡i
	 */
	public Boolean insertOrUpdateUser(UserInsuranceDto userInsuranceDto, AccountDto accountDto);
		
	/**
	 * PhÆ°Æ¡ng thá»©c kiá»ƒm tra tá»“n táº¡i mÃ£ sá»‘ tháº» báº£o hiá»ƒm
	 * @param insuranceNumber mÃ£ sá»‘ tháº» báº£o hiá»ƒm
	 * @param userInternalId id user
	 * @return true náº¿u Ä‘Ã£ tá»“n táº¡i vÃ  ngÆ°á»£c láº¡i
	 */
	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId);
	
	/**
	 * PhÆ°Æ¡ng thá»©c láº¥y dá»¯ liá»‡u theo Ä‘iá»�u kiá»‡n tÃ¬m kiáº¿m Ä‘á»ƒ export csv
	 * @param inforSearchDto InforSearchDto
	 * @param sortBy sortBy
	 * @return ScrollableResults
	 */
	public ScrollableResults getListDataToExport(InforSearchDto inforSearchDto, String sortBy);
	
	/**
	 * PhÆ°Æ¡ng thá»©c láº¥y thÃ´ng tin chi tiáº¿t hiá»ƒn thá»‹ MH03
	 * @param userID userInternalID
	 * @return UserInsuranceBean
	 */
	public UserInsuranceDto getDetailsInfor(int userID);
	
	/**
	 * PhÆ°Æ¡ng thá»©c láº¥y ra insuranceInternalID theo userInternalID
	 * @param userID userInternalID
	 * @return insuranceInternalID
	 */
	public int getInsuranceInternalID(int userID);
}
