package com.demo.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<User>
{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setUserName(rs.getString("username"));
		user.setPhone(rs.getString("phone"));
		user.setAddress(rs.getString("address"));
		user.setAge(rs.getString("age"));
		user.setWallet(rs.getInt("wallet"));
		return user;
	}
}
