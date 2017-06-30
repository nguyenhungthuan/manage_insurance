/**Copyright(C) 2016  [Cong ty CP phan mem Luvina]
 *UserDaoImpl.java, Dec 3, 2016 [Nguyễn Hưng Thuận]
 */
package net.luvina.manageinsurances.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.entities.CompanyBean;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
/**
 * Class UserDaoImpl
 * @author DELL
 *
 */
@Component
public class UserDaoImpl  {

	private Session session;
	
	private SessionFactory sessionFactory;
	
//	public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#checkExistedAcc(java.lang.String, java.lang.String)
//	 */
//	  @Transactional
//	public Boolean checkExistedAcc(String userName, String password) {
//		try{
//			session = this.sessionFactory.getCurrentSession();
//			StringBuilder hqlCommand = new StringBuilder();
//			hqlCommand.append("SELECT count(*) FROM " + UserBean.class.getName() + " u ");
//			hqlCommand.append("WHERE u.userName =:user AND u.password =:pass ");
//			Query query = session.createQuery(hqlCommand.toString());
//			query.setParameter("user", userName);
//			query.setParameter("pass", password);			
//			Long rs = (Long) query.getSingleResult();
//			if(rs > 0) {
//				return true;
//			}
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		return false;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#getListInfor(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@Transactional
//	public List<UserInsuranceBean> getListInfor(int company, String fullName, String code, String registerPlace,
//			String sortBy, String sortType, int limit, int offset) {
//		session = this.sessionFactory.getCurrentSession();
//		List<UserInsuranceBean> listUserIn = new ArrayList<UserInsuranceBean>();
//		try {
//			StringBuilder sqlCommand = new StringBuilder();
//			sqlCommand.append(
//					"SELECT new "+UserInsuranceBean.class.getName()+"(u.userInternalID, u.company.companyInternalId, u.company.companyName, u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
//			sqlCommand.append("FROM ");
//			sqlCommand.append(UserBean.class.getName() + " u ,");
//			sqlCommand.append(InsuranceBean.class.getName() + " i ");
//			sqlCommand.append(
//					"WHERE u.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
//			Map<String, Object> mapAttri = new HashMap<String, Object>();
//			Set set = mapAttri.entrySet();
//			Iterator iterator;
//			if (fullName.length() > 0) {
//				mapAttri.put("u.fullName", fullName);
//			}
//			if (code.length() > 0) {
//				mapAttri.put("i.insuranceNumber", code);
//			}
//			if (registerPlace.length() > 0) {
//				mapAttri.put("i.placeOfRegister", registerPlace);
//
//			}
//			if (mapAttri.size() > 0) {
//				iterator = set.iterator();
//				while (iterator.hasNext()) {
//					Map.Entry mapEntry = (Map.Entry) iterator.next();
//					sqlCommand.append(" AND ");
//					sqlCommand.append(mapEntry.getKey() + " LIKE ? ");
//				}
//			}
//			sqlCommand.append(" ORDER BY "+sortType +" "+ sortBy);
//			Query<UserInsuranceBean> query = session.createQuery(sqlCommand.toString(), UserInsuranceBean.class);
//			query.setFirstResult(offset);
//			query.setMaxResults(limit);
//			query.setParameter(0, company);
//			if (mapAttri.size() > 0) {
//				iterator = set.iterator();
//				int i = 1;
//				while (iterator.hasNext()) {
//					Map.Entry mapEntry = (Map.Entry) iterator.next();
//					query.setParameter(i++, "%"+mapEntry.getValue()+"%");
//				}
//			}
//			listUserIn = query.getResultList();
//		}  catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return listUserIn;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#getTotalUser(int, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public int getTotalRecords(int companyID, String fullName, String insuranceNumber, String registerPlace) {
//		StringBuilder sqlCommand = new StringBuilder();
//		session = this.sessionFactory.getCurrentSession();
//		Long result = (long) 0.0;
//		try {
//		sqlCommand.append("SELECT count(*) ");
//		sqlCommand.append("FROM ");
//		sqlCommand.append(UserBean.class.getName() + " u ,");
//		sqlCommand.append(InsuranceBean.class.getName() + " i ");
//		sqlCommand.append(
//				"WHERE u.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId = ? ");
//		Map<String, Object> mapAttri = new HashMap<String, Object>();
//		Set set =  mapAttri.entrySet();
//		Iterator iterator;
//		// kiểm tra các giá trị input, nếu không phải rỗng thì đưa vào map
//		if (fullName.length() > 0) {
//			mapAttri.put("u.fullName", fullName);
//		}
//		if (insuranceNumber.length() > 0) {
//			mapAttri.put("i.insuranceNumber", insuranceNumber);
//		}
//		if (registerPlace.length() > 0) {
//			mapAttri.put("i.placeOfRegister", registerPlace);
//		}
//		// nếu map.size > 0, add thêm các thuộc tính cần tìm kiếm
//		if (mapAttri.size() > 0) {
//			iterator = set.iterator();
//			// duyệt map
//			while (iterator.hasNext()) {
//				Map.Entry mapEntry = (Map.Entry) iterator.next();
//				// thêm AND, sử dụng like tìm kiếm tương đối
//				sqlCommand.append(" AND ");
//				sqlCommand.append(mapEntry.getKey() + " LIKE ? ");
//			}
//		}
//		Query<Long> query = session.createQuery(sqlCommand.toString());
//		// set giá trị cho các thuộc tính
//		query.setParameter(0, companyID);
//		if (mapAttri.size() > 0) {
//			iterator = set.iterator();
//			int i = 1;
//			while (iterator.hasNext()) {
//				Map.Entry mapEntry = (Map.Entry) iterator.next();
//				query.setParameter(i++, "%"+mapEntry.getValue()+"%");
//			}
//		}
//		// lấy số lượng bản ghi phù hợp
//		result = query.getSingleResult();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return result.intValue();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#checkExistedUser(int)
//	 */
//	@Transactional
//	public Boolean checkExistedUser(int userId) {
//		StringBuilder hql = new StringBuilder();
//		try {
//			session = this.sessionFactory.getCurrentSession();
//			hql.append("SELECT count(*) FROM " + UserBean.class.getName());
//			hql.append(" u WHERE u.userInternalID = ? ");
//			Query query = session.createQuery(hql.toString());
//			query.setParameter(0, userId);
//			Long result = (Long) query.getSingleResult();
//			if (result > 0) {
//				return true;
//			}
//		}catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		return false;
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#deleteUser(int)
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	public Boolean deleteUser(int userID) {
//		StringBuilder hqlCommand = new StringBuilder();
//		session = sessionFactory.getCurrentSession();
//		hqlCommand.append("SELECT u from " + UserBean.class.getName() + " u WHERE u.userInternalID =:userID");
//		UserBean userBean = (UserBean) session.createQuery(hqlCommand.toString()).setParameter("userID", userID).getSingleResult();
//		session.delete(userBean);
//		session.delete(userBean.getInsurance());
//		return true;
//	}
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#getUserById(int)
//	 */
//	@Transactional
//	public UserInsuranceBean getUserById(int userId) {
//		StringBuilder sql = new StringBuilder();
//		UserInsuranceBean userInsuranceBean = new UserInsuranceBean();
//		try{ 
//			session = this.sessionFactory.getCurrentSession();
//			sql.append("SELECT new "+UserInsuranceBean.class.getName()+"(u.userInternalID, c.companyInternalId, u.insuranceInternalId, u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
//			sql.append(" FROM ");
//			sql.append(UserBean.class.getName()+" u ,");
//			sql.append(CompanyBean.class.getName()+" c ,");
//			sql.append(InsuranceBean.class.getName()+ " i ");
//			sql.append("WHERE u.company.companyInternalId = c.companyInternalId AND u.insuranceInternalId = i.insuranceInternalId AND u.userInternalID = ?");
//			Query<UserInsuranceBean> query = session.createQuery(sql.toString(), UserInsuranceBean.class);
//			query.setParameter(0, userId);
//			userInsuranceBean = query.getSingleResult();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return userInsuranceBean;
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#insertOrUpdateUser(net.luvina.manageinsurances.entities.UserBean, net.luvina.manageinsurances.entities.InsuranceBean, net.luvina.manageinsurances.entities.CompanyBean)
//	 */
//	@Transactional(rollbackFor = Exception.class)
//	public Boolean insertOrUpdateUser(UserBean user, InsuranceBean insurance, CompanyBean company) {
//		session = this.sessionFactory.getCurrentSession();
//		if (company.getCompanyInternalId() == 0) {
//			session.save(company);
//		}
//		session.saveOrUpdate(insurance);
//		user.setInsuranceInternalId(insurance.getInsuranceInternalId());
//		session.saveOrUpdate(user);
//		return true;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#checkExistedInsuNum(int, int)
//	 */
//	@Transactional
//	public Boolean checkExistedInsuNum(String insuranceNumber, int userInternalId) {
//		try{
//			session = sessionFactory.getCurrentSession();
//			StringBuilder hqlCommand = new StringBuilder();
//			hqlCommand.append("SELECT count(*) FROM ");
//			hqlCommand.append(UserBean.class.getName()+" u, ");
//			hqlCommand.append(InsuranceBean.class.getName()+" i ");
//			hqlCommand.append("WHERE u.insuranceInternalId = i.insuranceInternalId AND u.userInternalID !=:userInternalId AND i.insuranceNumber =:insuranceNumber ");
//			Query query = session.createQuery(hqlCommand.toString());
//			query.setParameter("userInternalId", userInternalId);
//			query.setParameter("insuranceNumber", insuranceNumber);
//			Long rs = (Long) query.getSingleResult();
//			if(rs > 0) {
//				return true;
//			}
//	} catch(Exception ex) {
//			ex.printStackTrace();
//			System.out.println("Co loi khi kiem tra ton tai ma so the bao hiem");
//	}
//		return false;
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#getListDataToExport(int, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@SuppressWarnings("deprecation")
//	public ScrollableResults getListDataToExport(int companyID, String fullName, String insuranceNumber, String registerPlace, String sortType, String sortBy) {
//		StatelessSession session = this.sessionFactory.openStatelessSession();
//		session.beginTransaction();
//		try {
//			StringBuilder sqlCommand = new StringBuilder();
//			sqlCommand.append(
//					"SELECT new "+UserInsuranceBean.class.getName()+"(u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
//			sqlCommand.append("FROM ");
//			sqlCommand.append(UserBean.class.getName() + " u ,");
//			sqlCommand.append(InsuranceBean.class.getName() + " i ");
//			sqlCommand.append(
//					"WHERE u.insuranceInternalId = i.insuranceInternalId AND u.company.companyInternalId =:company ");
//			Map<String, List<String>> mapAttri = new HashMap<String, List<String>>();
//			List<String> listAttr;
//			Set set = mapAttri.entrySet();
//			Iterator iterator;
//			if (fullName.length() > 0) {
//				listAttr = new ArrayList<>();
//				listAttr.add(fullName);
//				listAttr.add("fullName");
//				mapAttri.put("u.fullName", listAttr);
//			}
//			if (insuranceNumber.length() > 0) {
//				listAttr = new ArrayList<>();
//				listAttr.add(insuranceNumber);
//				listAttr.add("insuranceNumber");
//				mapAttri.put("i.insuranceNumber", listAttr);
//			}
//			if (registerPlace.length() > 0) {
//				listAttr = new ArrayList<>();
//				listAttr.add(registerPlace);
//				listAttr.add("registerPlace");
//				mapAttri.put("i.placeOfRegister", listAttr);
//			}
//			if (mapAttri.size() > 0) {
//				iterator = set.iterator();
//				while (iterator.hasNext()) {
//					Map.Entry mapEntry = (Map.Entry) iterator.next();
//					sqlCommand.append(" AND ");
//					listAttr = mapAttri.get(mapEntry.getKey());
//					sqlCommand.append(mapEntry.getKey() + " LIKE :"+listAttr.get(1));
//				}
//			}
//			sqlCommand.append(" ORDER BY "+sortBy +" "+ sortType);
//			Query<UserInsuranceBean> query = session.createQuery(sqlCommand.toString(), UserInsuranceBean.class).setReadOnly(true).setFetchSize(Integer.MIN_VALUE);
//			query.setParameter("company", companyID);
//			if (mapAttri.size() > 0) {
//				iterator = set.iterator();
//				while (iterator.hasNext()) {
//					Map.Entry mapEntry = (Map.Entry) iterator.next();
//					listAttr = mapAttri.get(mapEntry.getKey());
//					query.setParameter(listAttr.get(1), "%"+listAttr.get(0)+"%");
//				}
//			}
//			ScrollableResults results = query.scroll(ScrollMode.FORWARD_ONLY);
//			return results;
//		}  catch (Exception ex) {
//			ex.printStackTrace();
//			return null;
//		}		
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#getDetailsInfor(int)
//	 */
//	@Transactional
//	public UserInsuranceBean getDetailsInfor(int userID) {
//		StringBuilder sql = new StringBuilder();
//		session = this.sessionFactory.getCurrentSession();
//		UserInsuranceBean userInsuranceBean = new UserInsuranceBean();
//		try{ 
//			sql.append("SELECT new "+UserInsuranceBean.class.getName()+"(u.userInternalID, c.companyInternalId, c.companyName, u.fullName, u.sex, u.birthday, i.insuranceNumber, i.insuranceStartDate, i.insuranceEndDate, i.placeOfRegister) ");
//			sql.append(" FROM ");
//			sql.append(UserBean.class.getName()+" u ,");
//			sql.append(CompanyBean.class.getName()+" c ,");
//			sql.append(InsuranceBean.class.getName()+ " i ");
//			sql.append("WHERE u.company.companyInternalId = c.companyInternalId AND u.insuranceInternalId = i.insuranceInternalId AND u.userInternalID = ?");
//			Query<UserInsuranceBean> query = session.createQuery(sql.toString(), UserInsuranceBean.class);
//			query.setParameter(0, userID);
//			userInsuranceBean = query.getSingleResult();			
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return userInsuranceBean;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see net.luvina.manageinsurances.dao.UserDao#getInsuranceNumber(int)
//	 */
//	@Transactional
//	public int getInsuranceInternalID(int userID) {
//		StringBuilder hqlCommand = new StringBuilder();
//		int insuranceNumber = 0;
//		try {
//			session = sessionFactory.getCurrentSession();
//			hqlCommand.append("SELECT u.insuranceInternalId FROM "+UserBean.class.getName()+" u ");
//			hqlCommand.append("WHERE u.userInternalID =:userID ");
//			Query query = session.createQuery(hqlCommand.toString());
//			query.setParameter("userID", userID);
//			insuranceNumber = (Integer) query.getSingleResult();			
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		return insuranceNumber;
//	}
}
	
