package com.demo.service;

import java.util.List;

import com.demo.model.Car;

public interface CarService {
	
	Car createCar(Car car);

	Car findOne(int car);

	List<Car> findAll();

	void update(Car car);

	void delete(int id);

	Car findByLicense(String license);

	boolean findUserMapping(int carId);
}
