package com.demo.service;

import java.util.List;

import com.demo.dao.UserDaoImpl;
import com.demo.model.User;

public class UserServiceImpl implements UserService {
	
	UserDaoImpl userDao;
	
	public UserServiceImpl(UserDaoImpl userDao) {
		super();
		this.userDao = userDao;
	}

	public void createUser(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}
	
	public User findOne(int id) {
		// TODO Auto-generated method stub
		return userDao.findOne(id);
	}
	
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}
	
	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}
	
	public void delete(int id) {
		// TODO Auto-generated method stub
		userDao.delete(id);
	}

}
