package net.luvina.manageinsurances.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.dao.InsuranceDao;
import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.dao.custom.UserDaoCustom;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;
import net.luvina.manageinsurances.entities.CompanyBean;

public class UserDaoImpl implements UserDaoCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private InsuranceDao insuranceDao;
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.UserDao#getListInfor(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<UserInsuranceBean> getListInfor(int company, String fullName, String code, String registerPlace,
			String sortBy, String sortType, int limit, int offset) {
		List<UserInsuranceBean> listUserIn = new ArrayList<UserInsuranceBean>();
		try {
			StringBuilder sqlCommand = new StringBuilder();
			sqlCommand.append(
					"SELECT new "+UserInsuranceBean.class.getName()+"(u.userInternalID, u.company.companyInternalId, u.company.companyName, u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
			sqlCommand.append("FROM ");
			sqlCommand.append(UserBean.class.getName() + " u ,");
			sqlCommand.append(InsuranceBean.class.getName() + " i ");
			sqlCommand.append(
					"WHERE u.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
			Map<String, Object> mapAttri = new HashMap<String, Object>();
			Set set = mapAttri.entrySet();
			Iterator iterator;
			if (fullName.length() > 0) {
				mapAttri.put("u.fullName", fullName);
			}
			if (code.length() > 0) {
				mapAttri.put("i.insuranceNumber", code);
			}
			if (registerPlace.length() > 0) {
				mapAttri.put("i.placeOfRegister", registerPlace);

			}
			if (mapAttri.size() > 0) {
				iterator = set.iterator();
				while (iterator.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					sqlCommand.append(" AND ");
					sqlCommand.append(mapEntry.getKey() + " LIKE ? ");
				}
			}
			sqlCommand.append(" ORDER BY "+sortType +" "+ sortBy);
			Query query = entityManager.createQuery(sqlCommand.toString());
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			query.setParameter(0, company);
			if (mapAttri.size() > 0) {
				iterator = set.iterator();
				int i = 1;
				while (iterator.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					if(mapEntry.getKey().equals("i.insuranceNumber")) {
						query.setParameter(i++, mapEntry.getValue());
					} else {
						query.setParameter(i++, "%"+mapEntry.getValue()+"%");
					}
				}
			}
			listUserIn = query.getResultList();
		}  catch (Exception ex) {
			ex.printStackTrace();
		}
		return listUserIn;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.UserDao#getTotalUser(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public int getTotalRecords(int companyID, String fullName, String insuranceNumber, String registerPlace) {
		StringBuilder sqlCommand = new StringBuilder();
		long result = 0;
		try {
		sqlCommand.append("SELECT count(*) ");
		sqlCommand.append("FROM ");
		sqlCommand.append(UserBean.class.getName() + " u ,");
		sqlCommand.append(InsuranceBean.class.getName() + " i ");
		sqlCommand.append(
				"WHERE u.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
		Map<String, Object> mapAttri = new HashMap<String, Object>();
		Set set =  mapAttri.entrySet();
		Iterator iterator;
		// kiểm tra các giá trị input, nếu không phải rỗng thì đưa vào map
		if (fullName.length() > 0) {
			mapAttri.put("u.fullName", fullName);
		}
		if (insuranceNumber.length() > 0) {
			mapAttri.put("i.insuranceNumber", insuranceNumber);
		}
		if (registerPlace.length() > 0) {
			mapAttri.put("i.placeOfRegister", registerPlace);
		}
		// nếu map.size > 0, add thêm các thuộc tính cần tìm kiếm
		if (mapAttri.size() > 0) {
			iterator = set.iterator();
			// duyệt map
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				// thêm AND, sử dụng like tìm kiếm tương đối
				sqlCommand.append(" AND ");
				sqlCommand.append(mapEntry.getKey() + " LIKE ? ");
			}
		}
		Query query = entityManager.createQuery(sqlCommand.toString());
		// set giá trị cho các thuộc tính
		query.setParameter(0, companyID);
		if (mapAttri.size() > 0) {
			iterator = set.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				if(mapEntry.getKey().equals("i.insuranceNumber")) {
					query.setParameter(i++, mapEntry.getValue());
				} else {
					query.setParameter(i++, "%"+mapEntry.getValue()+"%");
				}
			}
		}
		// lấy số lượng bản ghi phù hợp
		result = (Long) query.getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (int) result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.UserDao#checkExistedInsuNum(int, int)
	 */
	@Transactional
	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId) {
		try {
			StringBuilder hqlCommand = new StringBuilder();
			hqlCommand.append("SELECT count(*) FROM ");
			hqlCommand.append(UserBean.class.getName() + " u, ");
			hqlCommand.append(InsuranceBean.class.getName() + " i ");
			hqlCommand.append(
					"WHERE u.insuranceInternalId = i.insuranceInternalId AND u.userInternalID !=:userInternalId AND i.insuranceNumber =:insuranceNumber ");
			Query query = entityManager.createQuery(hqlCommand.toString());
			query.setParameter("userInternalId", userInternalId);
			query.setParameter("insuranceNumber", insuranceNumber);
			Long rs = (Long) query.getSingleResult();
			if (rs > 0) {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Co loi khi kiem tra ton tai ma so the bao hiem");
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.UserDao#getInsuranceNumber(int)
	 */
	@Transactional
	public int getInsuranceInternalID(int userID) {
		StringBuilder hqlCommand = new StringBuilder();
		int insuranceNumber = 0;
		try {
			hqlCommand.append("SELECT u.insuranceInternalId FROM "+UserBean.class.getName()+" u ");
			hqlCommand.append("WHERE u.userInternalID =:userID ");
			Query query = entityManager.createQuery(hqlCommand.toString());
			query.setParameter("userID", userID);
			insuranceNumber = (Integer) query.getSingleResult();			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return insuranceNumber;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.UserDao#insertOrUpdateUser(net.luvina.manageinsurances.entities.UserBean, net.luvina.manageinsurances.entities.InsuranceBean, net.luvina.manageinsurances.entities.CompanyBean)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean insertOrUpdateUser(UserBean user, InsuranceBean insurance, CompanyBean company) {
		if (company.getCompanyInternalId() == 0) {
			companyDao.save(company);
		}
		insuranceDao.save(insurance);
		user.setInsuranceInternalId(insurance.getInsuranceInternalId());
		userDao.save(user);
		return true;
	}
	
	@Transactional
	public UserInsuranceBean getDetailsInfor(int userID) {
		StringBuilder sql = new StringBuilder();
		UserInsuranceBean userInsuranceBean = new UserInsuranceBean();
		sql.append("SELECT new " + UserInsuranceBean.class.getName()
				+ "(u.userInternalID, c.companyInternalId, c.companyName, u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
		sql.append(" FROM ");
		sql.append(UserBean.class.getName() + " u ,");
		sql.append(CompanyBean.class.getName() + " c ,");
		sql.append(InsuranceBean.class.getName() + " i ");
		sql.append(
				"WHERE u.company.companyInternalId = c.companyInternalId AND u.insuranceInternalId = i.insuranceInternalId AND u.userInternalID = ?");
		Query query = entityManager.createQuery(sql.toString(), UserInsuranceBean.class);
		query.setParameter(0, userID);
		userInsuranceBean = (UserInsuranceBean) query.getSingleResult();
		return userInsuranceBean;
	}
}