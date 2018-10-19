package com.demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//This is custom Employee service
@Service
@Qualifier("customEmployeeService")
public class CustomEmployeeService implements EmployeeService {

}
