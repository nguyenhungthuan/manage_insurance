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
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
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
	int i;
	List<String> listAttr;
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.UserDao#getListInfor(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<UserInsuranceBean> getListInfor(int company, String fullName, String insuranceNumber, String placeOfRegister,
			String sortBy, String sortType, int limit, int offset) {
		List<UserInsuranceBean> listUserIn = new ArrayList<UserInsuranceBean>();
		try {
			StringBuilder sqlCommand = new StringBuilder();
			sqlCommand.append("SELECT new "+UserInsuranceBean.class.getName()+""
							+ "(u.userInternalID, u.company.companyInternalId, u.company.companyName, "
							+ "u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, "
							+ "i.insuranceEndDate, i.placeOfRegister) ");
			sqlCommand.append("FROM ");
			sqlCommand.append(UserBean.class.getName() + " u ,");
			sqlCommand.append(InsuranceBean.class.getName() + " i ");
			sqlCommand.append("WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
			Map<String, Object> mapAttri = checkAndSetDataToCommand(fullName, insuranceNumber, placeOfRegister, sqlCommand);
			sqlCommand.append(" ORDER BY "+sortType +" "+ sortBy);
			Query query = entityManager.createQuery(sqlCommand.toString());
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			query.setParameter(0, company);
			setValueOfParam(mapAttri, query);
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
	public int getTotalRecords(int companyID, String fullName, String insuranceNumber, String placeOfRegister) {
		StringBuilder sqlCommand = new StringBuilder();
		long result = 0;
		try {
		sqlCommand.append("SELECT count(*) ");
		sqlCommand.append("FROM ");
		sqlCommand.append(UserBean.class.getName() + " u ,");
		sqlCommand.append(InsuranceBean.class.getName() + " i ");
		sqlCommand.append("WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
		Map<String, Object> mapAttri = checkAndSetDataToCommand(fullName, insuranceNumber, placeOfRegister, sqlCommand);
		Query query = entityManager.createQuery(sqlCommand.toString());
		// set giá trị cho các thuộc tính
		query.setParameter(0, companyID);
		setValueOfParam(mapAttri, query);
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
					"WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.userInternalID <> :userInternalId AND i.insuranceNumber = :insuranceNumber ");
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
		UserBean user = userDao.findByUserInternalID(userID);
		return user == null ? 0 : user.getInsurance().getInsuranceInternalId();
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
		userDao.save(user);
		return true;
	}

	@Override
	public ScrollableResults getListDataToExport(int companyID, String fullName, String insuranceNumber,
			String registerPlace, String sortType, String sortBy) {
		try {
			StatelessSession statelessSession =
					entityManager.unwrap(Session.class).getSessionFactory().openStatelessSession();
			statelessSession.beginTransaction();
			StringBuilder sqlCommand = new StringBuilder();
			sqlCommand.append("SELECT new " + UserInsuranceBean.class.getName()
							+ "(u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, "
							+ "i.insuranceEndDate, i.placeOfRegister) ");
			sqlCommand.append("FROM ");
			sqlCommand.append(UserBean.class.getName() + " u ,");
			sqlCommand.append(InsuranceBean.class.getName() + " i ");
			sqlCommand.append("WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId =:company ");
			Map<String, List<String>> mapAttri = new HashMap<String, List<String>>();
			if (fullName.length() > 0) {
				listAttr = new ArrayList<>();
				listAttr.add(fullName);
				listAttr.add("fullName");
				mapAttri.put("u.fullName", listAttr);
			}
			if (insuranceNumber.length() > 0) {
				listAttr = new ArrayList<>();
				listAttr.add(insuranceNumber);
				listAttr.add("insuranceNumber");
				mapAttri.put("i.insuranceNumber", listAttr);
			}
			if (registerPlace.length() > 0) {
				listAttr = new ArrayList<>();
				listAttr.add(registerPlace);
				listAttr.add("registerPlace");
				mapAttri.put("i.placeOfRegister", listAttr);
			}
			if (!mapAttri.isEmpty()) {
				mapAttri.forEach((key, value) -> {
					sqlCommand.append(" AND ");
					listAttr = mapAttri.get(key);
					sqlCommand.append(key + " LIKE :" + listAttr.get(1));
				});
			}
			sqlCommand.append(" ORDER BY " + sortBy + " " + sortType);
			org.hibernate.query.Query<UserInsuranceBean> query =
					statelessSession.createQuery(sqlCommand.toString(), UserInsuranceBean.class).setReadOnly(true)
						.setFetchSize(Integer.MIN_VALUE);
			query.setParameter("company", companyID);
			if (!mapAttri.isEmpty()) {
				mapAttri.forEach((key, value) -> {
					listAttr = mapAttri.get(key);
					query.setParameter(listAttr.get(1), "%" + listAttr.get(0) + "%");
				});
			}
			ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
			return results;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Check parameter and set to command
	 * @param fullName full name
	 * @param insuranceNumber insurance number
	 * @param placeOfRegister place of register
	 * @param sqlCommand command
	 * @return Map<String, object>
	 */
	private Map<String, Object> checkAndSetDataToCommand(String fullName, String insuranceNumber, String placeOfRegister, StringBuilder sqlCommand) {
		Map<String, Object> mapAttri = new HashMap<String, Object>();
		// kiểm tra các giá trị input, nếu không phải rỗng thì đưa vào map
		if (fullName.length() > 0) {
			mapAttri.put("u.fullName", fullName);
		}
		if (insuranceNumber.length() > 0) {
			mapAttri.put("i.insuranceNumber", insuranceNumber);
		}
		if (placeOfRegister.length() > 0) {
			mapAttri.put("i.placeOfRegister", placeOfRegister);
		}
		// nếu map.size > 0, add thêm các thuộc tính cần tìm kiếm
		if (!mapAttri.isEmpty()) {
			mapAttri.forEach((key,value) -> {
				sqlCommand.append(" AND ");
				sqlCommand.append(key + " LIKE ? ");
			});
		}
		return mapAttri;
	}
	/**
	 * Set value to parameter of command
	 * @param mapAttri Map attribute
	 * @param query 
	 */
	private void setValueOfParam(Map<String, Object> mapAttri, Query query) {
		i = 1;
		if (!mapAttri.isEmpty()) {	
			mapAttri.forEach((key,value) -> {
				if(key.equals("i.insuranceNumber")) {
					query.setParameter(i++, value);
				} else {
					query.setParameter(i++, "%"+value+"%");
				}
			});
		}
	}
}