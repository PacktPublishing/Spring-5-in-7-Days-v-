package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.demo.dao.EmployeeDao;

//this is default employee service
@Service
@Qualifier("defaultEmployeeService")
public class DefaultEmployeeService implements EmployeeService {
	
	@Autowired
	EmployeeDao employeeDao;

}
