package com.demo.service;

import java.util.List;

import com.demo.model.Car;
import com.demo.model.User;

public interface UserService {
	User createUser(User user);

	User findOne(int user);

	List<User> findAll();

	void update(User user);

	void delete(int id);

	User findByUserName(String username);

	void selectCar(User user, Car car);

	boolean findCarMapping(String string, int userId);
}
