package com.jee.round44.account;

import java.util.List;
import java.util.Map;

import org.glassfish.jersey.internal.inject.Custom;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jee.round44.CommonDao;
import com.jee.round44.HibernateUtil;
import com.jee.round44.exception.CustomException;

public class UserDao implements CommonDao<UserModel> {

	@Override
	public List<UserModel> getAllDoc() throws CustomException {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			List<UserModel> userList = session.createQuery("FROM UserModel", UserModel.class).list();			
			return userList;
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage());
		}

	}

	@Override
	public UserModel save(UserModel entity) throws CustomException {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the user object
			int id = (int) session.save(entity);
			// commit transaction
			transaction.commit();
			return entity;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new CustomException(e.getLocalizedMessage());
		}

	}

	@Override
	public UserModel getById(int id) throws CustomException {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			UserModel entity = session.get(UserModel.class, id);
			return entity;
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage());
		}
	}

	@Override
	public UserModel update(UserModel entity) throws CustomException {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the user object
			session.update(entity);
			// commit transaction
			transaction.commit();
			return entity;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public boolean delete(int id) throws CustomException {
		try (Session session = HibernateUtil.getSessionFactory().openSession();) {
			UserModel entity = session.get(UserModel.class, id);
			session.delete(entity);
			return true;
		} catch (Exception e) {
			throw new CustomException(e.getLocalizedMessage());
		}
	}

	@Override
	public Map<String, Object> getWithPagination(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
