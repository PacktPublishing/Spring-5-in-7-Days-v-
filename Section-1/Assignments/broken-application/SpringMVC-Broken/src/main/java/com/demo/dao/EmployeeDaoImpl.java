package com.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.to.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	@Autowired
	public EmployeeDaoImpl(Employee employeeDTO){
		super();
		//Do something
	}

}
