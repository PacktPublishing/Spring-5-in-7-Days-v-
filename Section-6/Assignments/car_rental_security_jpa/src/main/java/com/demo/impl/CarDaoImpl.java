package com.demo.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.demo.dao.CarDao;
import com.demo.model.Car;

@Repository
public class CarDaoImpl implements CarDao {
		
	@PersistenceContext
	private EntityManager em;
	
	public Car save(Car car) {
		em.persist(car);
		return findOne(em.createNativeQuery("select max(car_id) from car").getFirstResult());
	}
	
	public Car findOne(int carId) {
		return em.find(Car.class, carId);
	}
	
	public Car findByLicense(String license) {
		Query q=em.createNamedQuery("Car.getCarByLicense");
		q.setParameter("licensePlate", license);
		return (Car) q.getResultList().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<Car> findAll() {
		return em.createNamedQuery("Car.findAll").getResultList();
	}
	
	public void update(Car car) {
		em.merge(car);
	}
	
	public void delete(int carId) {
		em.remove(em.find(Car.class, carId));
	}
	
	public boolean findUserMapping(int carId) {
		List list=em.createNativeQuery("select * from users where car_Id="+carId).getResultList();
		if(list.isEmpty()){
			return false;
		}else
			return true;
	}
}
