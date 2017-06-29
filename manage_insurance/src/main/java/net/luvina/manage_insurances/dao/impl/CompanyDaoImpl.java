package net.luvina.manageinsurances.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.luvina.manageinsurances.dao.CompanyDao;
import net.luvina.manageinsurances.entities.CompanyBean;

/**
 * Class CompanyDaoImpl
 * @author DELL
 *
 */
@Transactional
@Component
public class CompanyDaoImpl implements CompanyDao {	 
    private Session session; 
    @Autowired
    private SessionFactory sessionFactory;
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.CompanyDao#getAllCom()
	 */
	public List<CompanyBean> getAllCom() {
		List<CompanyBean> listCompany = new ArrayList<>();
		session = this.sessionFactory.getCurrentSession();
		String sql = "SELECT new "+CompanyBean.class.getName()+"(c.companyInternalId, c.companyName) FROM "
				+ CompanyBean.class.getName() + " c ";			
		Query<CompanyBean> query = session.createQuery(sql, CompanyBean.class);
		listCompany = query.getResultList();
		return listCompany;
	}
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.CompanyDao#getCompanyByID(int)
	 */
	public CompanyBean getCompanyByID(int id) {
		CompanyBean company = new CompanyBean();
		session = this.sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT new "+CompanyBean.class.getName()+"(c.companyInternalId, c.companyName, c.address, c.email, c.tel) ");
		hql.append("FROM "+CompanyBean.class.getName()+" c WHERE c.companyInternalId = ?");
		Query<CompanyBean> query = session.createQuery(hql.toString(), CompanyBean.class);
		query.setParameter(0, id);
		company = query.getSingleResult();
		return company;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.CompanyDao#checkExistedCom(int)
	 */
	public Boolean checkExistedCom(int companyID) {
		Boolean result = false;
		session = this.sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT count(*) FROM "+CompanyBean.class.getName());
		hql.append(" c WHERE c.companyInternalId = ?");
		Query query = session.createQuery(hql.toString());
		query.setParameter(0, companyID);
		Long rs = (Long) query.getSingleResult();
		if(rs > 0) {
			result = true;
		} 
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.CompanyDao#checkExistedEmail(java.lang.String)
	 */
	public Boolean checkExistedEmail(String email) {
		session = this.sessionFactory.getCurrentSession();
		StringBuilder hqlCommand = new StringBuilder();
		hqlCommand.append("SELECT count(*) FROM " + CompanyBean.class.getName() + " c ");
		hqlCommand.append("WHERE c.email = ? AND c.email != ''");
		Query query = session.createQuery(hqlCommand.toString());
		query.setParameter(0, email);
		Long rs = (Long) query.getSingleResult();
		if (rs > 0) {
			return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.luvina.manageinsurances.dao.CompanyDao#checkExistedTel(java.lang.String)
	 */
	public Boolean checkExistedTel(String tel) {
		session = this.sessionFactory.getCurrentSession();
		StringBuilder hqlCommand = new StringBuilder();
		hqlCommand.append("SELECT count(*) FROM " + CompanyBean.class.getName());
		hqlCommand.append(" c WHERE c.tel = ? AND c.tel != ''");
		Query query = session.createQuery(hqlCommand.toString());
		query.setParameter(0, tel);
		Long rs = (Long) query.getSingleResult();
		if (rs > 0) {
			return true;
		}
		return false;
	}
}
