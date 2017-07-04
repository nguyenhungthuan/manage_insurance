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

import org.springframework.transaction.annotation.Transactional;

import net.luvina.manageinsurances.dao.UserDao;
import net.luvina.manageinsurances.dao.custom.UserDaoCustom;
import net.luvina.manageinsurances.entities.InsuranceBean;
import net.luvina.manageinsurances.entities.UserBean;
import net.luvina.manageinsurances.entities.UserInsuranceBean;

public class UserDaoImpl implements UserDaoCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
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
	@SuppressWarnings("unchecked")
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
}
