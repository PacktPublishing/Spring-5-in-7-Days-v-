package com.demo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	
	private int userId;
	
	@NotEmpty(message = "Username can not be null!")
	private String userName; 
	
	@NotEmpty(message = "Phone can not be null!")
	private String phone;
	
	@NotEmpty(message = "Address can not be null!")
	private String address;
	
	@NotEmpty(message = "Age can not be null!")
	@Min(value=18,message="Age should not be less than 18 years!")
	private String age;
	
	@NotNull(message = "Wallet can not be null!")
	@Min(value=500,message="Balance in wallet should not be less than 500!")
	private int wallet;
	
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
}
