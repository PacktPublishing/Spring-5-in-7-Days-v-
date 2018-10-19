package com.demo.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Car;

import reactor.core.publisher.Mono;

@Repository
public interface CarDao extends ReactiveMongoRepository<Car, Integer>{

	@Query("{ 'licensePlate': ?0 }")
	Mono<Car> findByLicensePlate(final String licensePlate);
	
	@Query("{'user.userId': ?0}")
	Mono<Car> findByUserId(int userId);
}
