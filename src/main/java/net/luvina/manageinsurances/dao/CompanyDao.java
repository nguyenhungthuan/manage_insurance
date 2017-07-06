/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *CompanyDao.java, Dec 4, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.luvina.manageinsurances.entities.CompanyBean;

/**
 * Interface CompanyDao
 * @author DELL
 *
 */

public interface CompanyDao extends JpaRepository<CompanyBean, Integer> {
	
	public List<CompanyBean> findAll();
	
	public CompanyBean findByCompanyInternalId(int companyInternalId);
	
	public CompanyBean findByEmail(String email);
	
	public CompanyBean findByTel(String tel);
}
