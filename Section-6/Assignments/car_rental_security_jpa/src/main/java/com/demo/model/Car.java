package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licenseplate", columnNames = {"licensePlate"})
)

@NamedQuery(name="Car.getCarByLicense",query="SELECT c FROM Car c where c.licensePlate=:licensePlate") 
@NamedNativeQuery(name = "Car.findAll",query = "select * from car",resultClass=Car.class)
public class Car {
	
	@Id
    @GeneratedValue
    @Column(name="car_id")
	private int carId;
	
	@NotEmpty(message = "License Plate can not be null!")
    @Size(min = 5, max = 10)
	@Column(nullable = false)
	private String licensePlate; 
	
	@NotNull(message="Car cost cannot be null!")
	@Min(value=100,message="Rental cost should not be less than 100!")
	@Column(nullable = false)
	private int cost;
	
	@Column
	private String manufacturer;
	
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
}
