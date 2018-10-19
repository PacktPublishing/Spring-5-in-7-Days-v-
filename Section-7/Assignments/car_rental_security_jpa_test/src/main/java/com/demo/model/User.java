package com.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(name = "uc_username", columnNames = {"userName"})
)
@NamedQuery(name="User.getUsersByName",query="SELECT u FROM User u where u.userName=:userName") 
@NamedNativeQuery(name = "User.findAll",query = "select * from users",resultClass=User.class)
public class User {
	
	@Id
	@GeneratedValue
	private int userId;
	
	@NotEmpty(message = "Username can not be null!")
	@Column(nullable = false)
	private String userName; 
	
	@NotEmpty(message = "Phone can not be null!")
	@Column(nullable = false)
	private String phone;
	
	@NotEmpty(message = "Address can not be null!")
	@Column(nullable = false)
	private String address;
	
	@NotEmpty(message = "Age can not be null!")
	@Min(value=18,message="Age should not be less than 18 years!")
	@Column(nullable = false)
	private String age;
	
	@NotNull(message = "Wallet can not be null!")
	@Min(value=500,message="Balance in wallet should not be less than 500!")
	@Column(nullable = false)
	private int wallet;
	
	@OneToOne
    @JoinColumn(name="car_id")
    private Car car;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getWallet() {
		return wallet;
	}
	public void setWallet(int wallet) {
		this.wallet = wallet;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
}
