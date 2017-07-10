/**
 * Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 * InsuranceDao.java, Jul 5, 2017, 2017 nguyenhungthuan
 */
package net.luvina.manageinsurances.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;

/**
 * @author nguyenhungthuan
 *
 */
public interface InsuranceDao extends JpaRepository<InsuranceBean, Integer> {
	
}
