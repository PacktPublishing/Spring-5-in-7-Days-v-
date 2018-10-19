package com.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.UserDao;
import com.demo.model.User;
import com.demo.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
		
	@Transactional
	public Mono<User> createUser(User user) {
		return userDao.save(user);
	}
	
	@Transactional(readOnly = true)
	public Mono<User> findOne(int id) {
		return userDao.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Flux<User> findAll() {
		return userDao.findAll();
	}
	
	@Transactional
	public Mono<User> update(User user) {
		return userDao.save(user);
	}
	
	@Transactional
	public Mono<Void> delete(int id) {
		return userDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Flux<User> findByUserName(String username) {
		return userDao.findByUserName(username);
	}

	@Override
	public boolean findCarMapping(int carId) {
		if(userDao.findByCarId(carId)!=null){
			return true;
		}
		return false;
	}

}
