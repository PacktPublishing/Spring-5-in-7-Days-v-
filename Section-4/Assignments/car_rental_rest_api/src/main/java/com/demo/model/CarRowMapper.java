package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CarRowMapper implements RowMapper<Car>
{
	public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
		Car car = new Car();
		car.setCarId(rs.getInt("car_id"));
		car.setLicensePlate(rs.getString("license_plate"));
		car.setCost(rs.getInt("cost"));
		car.setManufacturer(rs.getString("manufacturer"));
		return car;
	}
}
