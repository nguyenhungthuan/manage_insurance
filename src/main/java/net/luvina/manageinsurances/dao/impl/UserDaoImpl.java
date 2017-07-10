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
import org.hibernate.SessionFactory;
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
					"WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
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
				"WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
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
		if(userDao.findByUserInternalID(userID) == null) {
			return 0;
		}
		return userDao.findByUserInternalID(userID).getInsurance().getInsuranceInternalId();
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
			StatelessSession statelessSession = entityManager.unwrap(Session.class).getSessionFactory().openStatelessSession();
			statelessSession.beginTransaction();
				StringBuilder sqlCommand = new StringBuilder();
				sqlCommand.append(
						"SELECT new "+UserInsuranceBean.class.getName()+"(u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
				sqlCommand.append("FROM ");
				sqlCommand.append(UserBean.class.getName() + " u ,");
				sqlCommand.append(InsuranceBean.class.getName() + " i ");
				sqlCommand.append(
						"WHERE u.insurance.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId =:company ");
				Map<String, List<String>> mapAttri = new HashMap<String, List<String>>();
				List<String> listAttr;
				Set set = mapAttri.entrySet();
				Iterator iterator;
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
				if (mapAttri.size() > 0) {
					iterator = set.iterator();
					while (iterator.hasNext()) {
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						sqlCommand.append(" AND ");
						listAttr = mapAttri.get(mapEntry.getKey());
						sqlCommand.append(mapEntry.getKey() + " LIKE :"+listAttr.get(1));
					}
				}
				sqlCommand.append(" ORDER BY "+sortBy +" "+ sortType);
				org.hibernate.query.Query<UserInsuranceBean> query = statelessSession.createQuery(sqlCommand.toString(), UserInsuranceBean.class).setReadOnly(true).setFetchSize(Integer.MIN_VALUE);
				query.setParameter("company", companyID);
				if (mapAttri.size() > 0) {
					iterator = set.iterator();
					while (iterator.hasNext()) {
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						listAttr = mapAttri.get(mapEntry.getKey());
						query.setParameter(listAttr.get(1), "%"+listAttr.get(0)+"%");
					}
				}
				ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
				return results;
			}  catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}		
		}
	}