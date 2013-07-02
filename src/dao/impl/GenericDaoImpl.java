package dao.impl;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import dao.GenericDao;

public class GenericDaoImpl implements GenericDao {
	
//	private SessionFactory sessionFactory;
////
////	@Autowired
////	public void setSessionFactory( SessionFactory sessionFactory ) {
////		this.sessionFactory = sessionFactory;
////	}
////	
//	public Session getSession() {
//		return sessionFactory.getCurrentSession();
//	}
	
	@Autowired
	private Session session;
	
	public Session getSession() {
		return session;
	}

	public boolean save(Object object) {
		try {
			getSession().save(object);
			getSession().flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
		
	public boolean update(Object object) {
		try {
			getSession().merge(object);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void delete(Object object) {
		try {
			getSession().delete(object);
			getSession().flush();
		} catch (ConstraintViolationException e) {
			throw e;
		}
	}
}