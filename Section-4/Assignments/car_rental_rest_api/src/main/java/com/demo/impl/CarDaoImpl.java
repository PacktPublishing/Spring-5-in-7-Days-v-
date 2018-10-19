package com.demo.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.demo.dao.CarDao;
import com.demo.model.Car;
import com.demo.model.CarRowMapper;
import com.demo.model.UserCar;

@Repository
public class CarDaoImpl implements CarDao {
	
	private CarRowMapper carRowMapper;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CarDaoImpl(CarRowMapper carRowMapper, JdbcTemplate jdbcTemplate) {
		super();
		this.carRowMapper = carRowMapper;
		this.jdbcTemplate = jdbcTemplate;
		createTable();
	}

	public Car save(Car car) {
		String queryCar = "insert into cars (license_plate, cost, manufacturer) values (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(queryCar,Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, car.getLicensePlate());
	        ps.setInt(2, car.getCost());
	        ps.setString(3, car.getManufacturer());
	        return ps;
	     }, keyHolder);
		return findOne(keyHolder.getKey().intValue());
	}
	
	public Car findOne(int id) {
		String queryCar = "select * from cars where car_id=?";
		try{
		return (Car) jdbcTemplate.queryForObject(queryCar, new Object[] { id },carRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public Car findByLicense(String license) {
		String queryCar = "select * from cars where license_plate=?";
		try{
		return (Car) jdbcTemplate.queryForObject(queryCar, new Object[] { license },carRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public List<Car> findAll() {
		createTable();
		String queryCar = "select * from cars";
		List<Car> cars =jdbcTemplate.query(queryCar,new BeanPropertyRowMapper<Car>(Car.class));
		return cars;
	}
	
	public void update(Car car) {
		String queryCar = "update cars set license_plate=?, cost=?, manufacturer=? where car_id=?";
		jdbcTemplate.update(queryCar, new Object[] { car.getLicensePlate(),car.getCost(),car.getManufacturer(),car.getCarId() });
	}
	
	public void delete(int id) {
		String queryCar = "delete from cars where car_id=?";
		jdbcTemplate.update(queryCar, new Object[] { id });
	}
	
	public void createTable(){
		String query= "create table IF NOT EXISTS cars(car_id int not null auto_increment, license_plate varchar(10) not null ,cost int(10) not null, manufacturer varchar(10) null, PRIMARY KEY (car_id))";
		jdbcTemplate.execute(query);
	}

	public boolean findUserMapping(int carId) {
		// TODO Auto-generated method stub
		try{
			jdbcTemplate.queryForObject("select * from user_car where carId=?", new Object[] { carId },new BeanPropertyRowMapper<UserCar>(UserCar.class));
			return true;
		}catch(EmptyResultDataAccessException e){
			return false;
		}
	}
}
