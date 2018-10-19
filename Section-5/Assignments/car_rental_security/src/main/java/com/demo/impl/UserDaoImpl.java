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

import com.demo.dao.UserDao;
import com.demo.model.User;
import com.demo.model.UserCar;
import com.demo.model.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {
	
	private UserRowMapper userRowMapper;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDaoImpl(UserRowMapper userRowMapper, JdbcTemplate jdbcTemplate) {
		super();
		this.userRowMapper = userRowMapper;
		this.jdbcTemplate = jdbcTemplate;
		createTable();
	}

	public User save(User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String queryUser = "insert into users (username, phone, address, age, wallet) values (?,?,?,?,?)";
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection.prepareStatement(queryUser,Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, user.getUserName());
	        ps.setString(2, user.getPhone());
	        ps.setString(3, user.getAddress());
	        ps.setString(4, user.getAge());
	        ps.setInt(5, user.getWallet());
	        return ps;
	     }, keyHolder);
		return findOne(keyHolder.getKey().intValue());
	}
	
	public User findOne(int id) {
		String queryUser = "select * from users where user_id=?";
		try{
		return (User) jdbcTemplate.queryForObject(queryUser, new Object[] { id },userRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public List<User> findAll() {
		createTable();
		String queryUser = "select * from users";
		List<User> users =jdbcTemplate.query(queryUser,new BeanPropertyRowMapper<User>(User.class));
		return users;
	}
	
	public void update(User user) {
		String queryUser = "update users set username=?,phone=?,address=?,age=?,wallet=? where user_id=?";
		jdbcTemplate.update(queryUser, new Object[] { user.getUserName(),user.getPhone(),user.getAddress(),user.getAge(),user.getWallet(),user.getUserId() });
	}
	
	public void delete(int id) {
		String queryUser = "delete from users where user_id=?";
		jdbcTemplate.update(queryUser, new Object[] { id });
	}
	
	public User findByUserName(String username) {
		String queryUser = "select * from users where username=?";
		try{
		return (User) jdbcTemplate.queryForObject(queryUser, new Object[] { username },userRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	public void createTable(){
		String query= "create table IF NOT EXISTS users(user_id int not null auto_increment, username varchar(10) not null ,phone varchar(10) not null,address varchar(500) not null,age int(2) not null,wallet int(3) not null, PRIMARY KEY (user_id))";
		jdbcTemplate.execute(query);
		query= "create table IF NOT EXISTS user_car(userId int not null, carId int not null, PRIMARY KEY (userId, carId), FOREIGN KEY (userId) REFERENCES USERS(USER_ID), FOREIGN KEY (carId) REFERENCES CARS(car_id))";
		jdbcTemplate.execute(query);
	}

	public void selectCar(int userId, int carId) {
		String query="insert into user_car (userId, carId) values (?,?)";
		jdbcTemplate.update(query, new Object[] { userId,carId });
	}

	public boolean findCarMapping(int userId) {
		try{
			jdbcTemplate.queryForObject("select * from user_car where userId=?", new Object[] { userId },new BeanPropertyRowMapper<UserCar>(UserCar.class));
			return true;
		}catch(EmptyResultDataAccessException e){
			return false;
		}
	}
}
