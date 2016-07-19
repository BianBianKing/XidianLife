package com.xidian.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.UserDao;
import com.xidian.forms.User;



@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao{
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	public User getUserByEmail(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User user where user.username=:email").setParameter("email", email);
		List<User> users=query.list();
		if(users.isEmpty()) {
			return null;
		} else {
			return (User) query.list().get(0); 
		}
	}
	@Override
	public void addUser(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
	}
	@Override
	public void deleteUser(long id) {
		Session session=sessionFactory.getCurrentSession();
		User user = (User)session.get(User.class,id);
		session.delete(user);
	}
	@Override
	public void updateUser(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.update(user);
	}
	@Override
	public String getPassword(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from User user where user.email=:email").setParameter("email", email);
		List<User> users=query.list();
		if(users.isEmpty()) {
			return null;
		} else {
			return ((User) query.list().get(0)).getPassword(); 
		}
	}
	@Override
	public User getUserById(Long id) {
		Session session=sessionFactory.getCurrentSession();
		User user = (User)session.get(User.class,id);
		return user;
	}
}
