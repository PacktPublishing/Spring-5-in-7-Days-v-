package com.demo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.exception.ErrorCode;
import com.demo.exception.ProhibitedOperationException;
import com.demo.model.Car;
import com.demo.model.User;
import com.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	UserDaoImpl userDao;
	
	@Autowired
	public UserServiceImpl(UserDaoImpl userDao) {
		super();
		this.userDao = userDao;
	}
	
	@Transactional
	public User createUser(User user) {
		return userDao.save(user);
	}
	
	@Transactional(readOnly = true)
	public User findOne(int id) {
		return userDao.findOne(id);
	}
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	@Transactional
	public void update(User user) {
		userDao.update(user);
	}
	
	@Transactional
	public void delete(int id) {
		userDao.delete(id);
	}

	@Transactional(readOnly = true)
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Transactional
	public void selectCar(User user, Car car) {
		userDao.selectCar(user.getUserId(),car.getCarId());
		int remainingBal=user.getWallet()-car.getCost();
		user.setWallet(remainingBal);
		userDao.update(user);
	}

	@Override
	public boolean findCarMapping(int userId) {
		// TODO Auto-generated method stub
		return userDao.findCarMapping(userId);
	}

}
