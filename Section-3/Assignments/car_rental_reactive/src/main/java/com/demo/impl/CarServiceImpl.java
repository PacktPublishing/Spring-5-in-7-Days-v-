package com.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.CarDao;
import com.demo.model.Car;
import com.demo.service.CarService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	CarDao carDao;

	public Mono<Car> createCar(Car car) {
		return carDao.save(car);
	}
	
	public Mono<Car> findOne(int id) {
		return carDao.findById(id);
	}
	
	public Mono<Car> findByLicense(String license) {
		return carDao.findByLicensePlate(license);
	}
	
	public Flux<Car> findAll() {
		return carDao.findAll();
	}
	
	public Mono<Car> update(Car car) {
		return carDao.save(car);
	}
	
	public Mono<Void> delete(int id) {
		return carDao.deleteById(id);
	}

	@Override
	public boolean findUserMapping(int userId) {
		// TODO Auto-generated method stub
		if(carDao.findByUserId(userId)!=null){
			return true;
		}else{
			return false;
		}
	}

}
