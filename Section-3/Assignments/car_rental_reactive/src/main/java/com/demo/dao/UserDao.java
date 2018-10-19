package com.demo.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserDao extends ReactiveMongoRepository<User, Integer>{

	@Query("{ 'userName': ?0 }")
	Flux<User> findByUserName(final String userName);
	
	@Query("{'car.carId': ?0}")
	Mono<User> findByCarId(int carId);
}
