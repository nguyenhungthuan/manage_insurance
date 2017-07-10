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
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	public List<CompanyBean> findAll();
	
	/**
	 * Find company by commpany id
	 * @param companyInternalId
	 * @return CompanyBean
	 */
	public CompanyBean findByCompanyInternalId(int companyInternalId);
	
	/**
	 * Find company by email
	 * @param email email
	 * @return CompanyBean
	 */
	public CompanyBean findByEmail(String email);
	
	/**
	 * Find by tel
	 * @param tel telephone
	 * @return CompanyBean
	 */
	public CompanyBean findByTel(String tel);
}
