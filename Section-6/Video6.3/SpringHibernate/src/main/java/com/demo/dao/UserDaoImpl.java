package com.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext(unitName="JPAPersistence")
	private EntityManager em;
	
	@PersistenceUnit(name="JPAPersistence")
	private EntityManagerFactory emf;
	
	/*@Autowired
	private SessionFactory sessionFactory;*/
	
	@Override
	public void save(User user) {
		em.persist(user);
		//sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public User findOne(int id) {
		return em.find(User.class, id);
		//Session session = this.sessionFactory.getCurrentSession();
		//User user = (User) session.load(User.class, new Integer(id));
		//return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByUserName(String userName) {
		Query q=em.createNamedQuery("User.getUsersByName");
		q.setParameter("userName", userName);
		return q.getResultList();
		/*org.hibernate.Session session = this.sessionFactory.getCurrentSession();
		org.hibernate.query.Query query = session.createNamedQuery("User.getUsersByName");
		query.setParameter("userName", userName);
		final List<User> list = query.list();
		return list;*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		return em.createNamedQuery("User.findAll").getResultList();
		//return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	@Override
	public void update(User user) {
		em.merge(user);
		//sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void delete(int id) {
		em.remove(em.find(User.class, id));
		/*Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, new Integer(id));
		if (user != null) {
			session.delete(user);
		}*/
	}

}
