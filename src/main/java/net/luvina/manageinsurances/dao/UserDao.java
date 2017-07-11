/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserDao.java, Dec 3, 2016 [Nguyá»…n HÆ°ng Thuáº­n]
 */
package net.luvina.manageinsurances.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.dao.custom.UserDaoCustom;

/**
 * Interface UserDao
 * @author DELL
 *
 */
@Repository
public interface UserDao extends JpaRepository<UserBean, Integer>, UserDaoCustom {

	/**
	 * Find by user name and password
	 * @param userName user name
	 * @param password pass
	 * @return List<UserBean>
	 */
	public List<UserBean> findByUserNameAndPassword(String userName, String password);
	
	/**
	 * Find by user internal id
	 * @param userInternalID userInternalID
	 * @return UserBean
	 */
	public UserBean findByUserInternalID(int userInternalID);
	
}
