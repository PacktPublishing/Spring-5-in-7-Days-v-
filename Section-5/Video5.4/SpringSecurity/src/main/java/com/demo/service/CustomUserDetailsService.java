package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.demo.model.CustomRowMapper;

import org.springframework.security.core.userdetails.User;

/**
 * @author ankidaemon
 *
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	DriverManagerDataSource dataSource;
	
	@Autowired
	CustomRowMapper customRowMapper;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.demo.model.User userTo = getUserToDetail(username);
		User userDetail = new User(
				userTo.getUserName(), userTo.getPassword(), userTo.isEnabled(), true, true, true, getAuthorities());
		return userDetail;
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authList;
	}

	public com.demo.model.User getUserToDetail(String userName) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String queryUser = "select * from user where userName=?";
		try{
			return  jdbcTemplate.queryForObject(queryUser, new Object[] { userName },customRowMapper);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

}
