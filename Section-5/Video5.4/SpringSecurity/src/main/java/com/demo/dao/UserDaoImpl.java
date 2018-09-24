package com.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import com.demo.model.CustomRowMapper;
import com.demo.model.User;

@Repository
public class UserDaoImpl implements UserDao {
		
	@Autowired
	private CustomRowMapper customRowMapper;
			
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDaoImpl(DriverManagerDataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		createTable(dataSource);
	}

	@Override
	public void save(User user) {
		String queryUser = "insert into user (userId, userName, phone) values (?,?,?)";
		jdbcTemplate.update(queryUser, new Object[] { user.getUserId(),user.getUserName(),user.getPhone() });
	}

	@Override
	public User findOne(int id) {
		String queryUser = "select * from user where userId=?";
		try{
		return (User) jdbcTemplate.queryForObject(queryUser, new Object[] { id },customRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<User> findByUserName(String userName) {
		String queryUser = "select * from user where userName=?";
		try{
		return jdbcTemplate.query(queryUser, new Object[] { userName },new BeanPropertyRowMapper<User>(User.class));
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<User> findAll() {
		String queryUser = "select * from user";
		List<User> users =jdbcTemplate.query(queryUser,new BeanPropertyRowMapper<User>(User.class));
		return users;
	}

	@Override
	public void update(User user) {
		String queryUser = "update user set userName=?,phone=? where userId=?";
		jdbcTemplate.update(queryUser, new Object[] { user.getUserName(),user.getPhone(),user.getUserId() });
	}

	@Override
	public void delete(int id) {
		String queryUser = "delete from user where userId=?";
		jdbcTemplate.update(queryUser, new Object[] { id });
	}
	
	public void createTable(DriverManagerDataSource dataSource){
		Resource resource = new ClassPathResource("import.sql");
	    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
	    databasePopulator.execute(dataSource);
	}

}
