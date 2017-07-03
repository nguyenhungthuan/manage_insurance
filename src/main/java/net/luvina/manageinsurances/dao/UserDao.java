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
import net.luvina.manageinsurances.dao.custom.UserDaoCustom;

/**
 * Interface UserDao
 * @author DELL
 *
 */
@Repository
public interface UserDao extends JpaRepository<UserBean, Integer>, UserDaoCustom {
	
	
	/**
	 * Phương thức tìm kiếm user theo username và password
	 * @param userName user name
	 * @param password pass
	 * @return true, false
	 */
	public List<UserBean> findByUserNameAndPassword(String userName, String password);
}
