package com.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.model.Car;
import com.demo.service.CarService;

/**
 * @author ankidaemon
 *
 */
@Controller
public class CarController {
	
	@Autowired
	CarService service;
	
	@GetMapping("/car/home")
	public ModelAndView carHome() {
		return new ModelAndView("car_signup");
	}

	@PostMapping("/car/searchById")
	public ModelAndView getRecord(@RequestParam(name="carid") int carId) {
		ModelAndView mav = new ModelAndView("carinfo");
		Car u=service.findOne(carId);
		if (u!=null) {
			mav.addObject("car",u);
		}else{
			mav.addObject("error","No Car found having eId "+carId);
		}
		return mav;
	}
	
	@PostMapping("/car/registration")
	public ModelAndView register(@Valid Car car,BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()){
			mav.addObject("errorList",createErrorList(result));
			mav.setViewName("car_signup");
		}else if(service.findByLicense(car.getLicensePlate())!=null){
			mav.addObject("error","Car is aleady registered with us.");
			mav.setViewName("car_signup");
		}else{
			mav.setViewName("carinfo");
			car=service.createCar(car);
			mav.addObject("car",car);
			mav.addObject("success","Registration Succesful");
		}
		return mav;
	}
	
	@GetMapping("/car/info")
	public ModelAndView visitInfo() {
		return new ModelAndView("carinfo");
	}
	
	@RequestMapping(value = "/car/all",method = RequestMethod.GET)
	public ModelAndView findAll() {
		ModelAndView mav = new ModelAndView("car");
		mav.addObject("carList",service.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/car/updatePage", method = RequestMethod.POST)
	public ModelAndView renderUpdatePage(@RequestParam(value="id") int id, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		mav.setView(new RedirectView("/car/info#update",true));
		Car car=service.findOne(id);
		car.setCarId(id);
		rattr.addFlashAttribute("toUpdate",car);
		return mav;
	}
		
	@PostMapping("/car/update")
	public ModelAndView update(@Valid Car car,BindingResult results) {
		ModelAndView mav = new ModelAndView();
		if(results.hasErrors()){
			mav.addObject("errorList",createErrorList(results));
			mav.setView(new RedirectView("/car/info#update",true));
		}else{
			service.update(car);
			mav.addObject("success","Car details updates succesfully.");
			mav.addObject("car",car);
			mav.setViewName("carinfo");
		}
		return mav;
	}
	
	@PostMapping("/car/delete")
	public ModelAndView deleteCar(@RequestParam("carId") int carId) {
		ModelAndView mav = new ModelAndView("car_signup");
		service.delete(carId);
		mav.addObject("success","Car deleted sucessfully.");
		return mav;
	}
	
	List<String> createErrorList(BindingResult result){
		List<String> errorList = new ArrayList<String>();
		for (Object object : result.getAllErrors()) {
		    if(object instanceof FieldError) {
		        FieldError fieldError = (FieldError) object;
		        errorList.add(fieldError.getCode()+" "+fieldError.getDefaultMessage());
		    }
		    else if(object instanceof ObjectError) {
		        ObjectError objectError = (ObjectError) object;
		        errorList.add(objectError.getCode()+" "+objectError.getDefaultMessage());
		    }
		}
		return errorList;
	}
}
