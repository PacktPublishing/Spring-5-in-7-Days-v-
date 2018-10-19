package com.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.exception.ErrorCode;
import com.demo.exception.ProhibitedOperationException;
import com.demo.model.Car;
import com.demo.model.ErrorResponse;
import com.demo.model.User;
import com.demo.model.UserCar;
import com.demo.service.CarService;
import com.demo.service.UserService;

/**
 * @author ankidaemon
 *
 */
@RestController
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	CarService carService;
	
	@GetMapping("/user/searchById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User getRecord(@PathVariable(name="id") int userId) {
		return service.findOne(userId);
	}
	
	@PostMapping("/user/registration")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> register(@Valid @RequestBody User user,BindingResult result,UriComponentsBuilder uCB) {
		if(result.hasErrors()){
			return ResponseEntity.badRequest().body(createErrorList(result));
		}
		else if(service.findByUserName(user.getUserName())!=null){
			return ResponseEntity.badRequest().body(new ErrorResponse("101","Username already in use."));
		}else{
			user=service.createUser(user);
			UriComponents uc = uCB.path("/user/searchById/{id}").buildAndExpand(user.getUserId());
			return ResponseEntity.created(uc.toUri()).build();
		}
	}
		
	@RequestMapping(value = "/user/all",method = RequestMethod.GET)
	public List<User> findAll() {
		return service.findAll();
	}

	@PutMapping("/user/update")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody User user) {
		service.update(user);
	}
	
	@DeleteMapping("/user/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable("id") int userId) {
		service.delete(userId);
	}
	
    @PostMapping("/user/carselection")
    @ResponseStatus(HttpStatus.OK)
    public void carSelection(@RequestBody UserCar userCar) throws ProhibitedOperationException
    {
    	User user=service.findOne(userCar.getUserId());
        Car car=carService.findOne(userCar.getCarId());
        if(service.findCarMapping(user.getUserId())){
			throw new ProhibitedOperationException(ErrorCode.ONEUSERPERCAR.getDescription(),ErrorCode.ONEUSERPERCAR);
		}else if(carService.findUserMapping(car.getCarId())){
			throw new ProhibitedOperationException(ErrorCode.ALREADYINUSE.getDescription(),ErrorCode.ALREADYINUSE);
		}else if(user.getWallet()<car.getCost()){
        	throw new ProhibitedOperationException(ErrorCode.INSUFFICIENTCASH.getDescription(),ErrorCode.INSUFFICIENTCASH);
        }else{
			service.selectCar(user, car);
		}
    }
    
    List<ErrorResponse> createErrorList(BindingResult result){
		List<ErrorResponse> errorList = new ArrayList<ErrorResponse>();
		for (Object object : result.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
		        errorList.add(new ErrorResponse(fieldError.getCode(),fieldError.getDefaultMessage()));
		    }
		    else if(object instanceof ObjectError) {
		        ObjectError objectError = (ObjectError) object;
		        errorList.add(new ErrorResponse(objectError.getCode(),objectError.getDefaultMessage()));
		    }
		}
		return errorList;
	}
}
