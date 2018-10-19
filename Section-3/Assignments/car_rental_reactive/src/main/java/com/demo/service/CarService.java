package com.demo.service;

import com.demo.model.Car;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarService {
	
	Mono<Car> createCar(Car car);

	Mono<Car> findOne(int car);

	Flux<Car> findAll();

	Mono<Car> update(Car car);

	Mono<Void> delete(int id);

	Mono<Car> findByLicense(String license);

	boolean findUserMapping(int carId);
}
