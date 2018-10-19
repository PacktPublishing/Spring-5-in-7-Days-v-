package com.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.demo.model.Car;
import com.demo.service.CarService;

/**
 * @author ankidaemon
 *
 */
@RestController
public class CarController {
	
	@Autowired
	CarService service;
	
	@GetMapping("/car/searchById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Car getRecord(@PathVariable(name="id") int carId) {
		return service.findOne(carId);
	}
	
	@PostMapping("/car/registration")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> register(@RequestBody Car car,UriComponentsBuilder uCB) {
		if(service.findByLicense(car.getLicensePlate())!=null){
			return ResponseEntity.badRequest().body("Car is Already Registered with Us");
		}else{
			car=service.createCar(car);
			UriComponents uc = uCB.path("/car/searchById/{id}").buildAndExpand(car.getCarId());
			return ResponseEntity.created(uc.toUri()).build();
		}
	}
		
	@RequestMapping(value = "/car/all",method = RequestMethod.GET)
	public List<Car> findAll() {
		return service.findAll();
	}
			
	@PutMapping("/car/update")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Car car) {
		service.update(car);
	}
	
	@DeleteMapping("/car/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCar(@PathVariable("id") int carId) {
		service.delete(carId);
	}

}
