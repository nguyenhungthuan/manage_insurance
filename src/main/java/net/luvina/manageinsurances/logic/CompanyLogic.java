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
}
