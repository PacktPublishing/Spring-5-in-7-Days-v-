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

import com.demo.exception.ErrorCode;
import com.demo.exception.ProhibitedOperationException;
import com.demo.model.Car;
import com.demo.model.User;
import com.demo.service.CarService;
import com.demo.service.UserService;

/**
 * @author ankidaemon
 *
 */
@Controller
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	CarService carService;
	
	@GetMapping("/user/home")
	public ModelAndView userHome() {
		return new ModelAndView("user_signup");
	}

	@PostMapping("/user/searchById")
	public ModelAndView getRecord(@RequestParam(name="userid") int userId) {
		ModelAndView mav = new ModelAndView("userinfo");
		User u=service.findOne(userId);
		if (u!=null) {
			mav.addObject("user",u);
			mav.addObject("carList",carService.findAll());
		}else{
			mav.addObject("error","No User found having eId "+userId);
		}
		return mav;
	}
	
	@PostMapping("/user/registration")
	public ModelAndView register(@Valid User user,BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()){
			mav.addObject("errorList",createErrorList(result));
			mav.setViewName("user_signup");
		}else if(service.findByUserName(user.getUserName())!=null){
			mav.addObject("error","Please select unique username");
			mav.setViewName("user_signup");
		}else{
			mav.setViewName("userinfo");
			user=service.createUser(user);
			mav.addObject("user",user);
			mav.addObject("carList",carService.findAll());
			mav.addObject("success","Registration Succesful");
		}
		return mav;
	}
	
	@GetMapping("/user/info")
	public ModelAndView visitInfo() {
		return new ModelAndView("userinfo");
	}
	
	@RequestMapping(value = "/user/all",method = RequestMethod.GET)
	public ModelAndView findAll() {
		ModelAndView mav = new ModelAndView("user");
		mav.addObject("userList",service.findAll());
		return mav;
	}
	
	@RequestMapping(value = "/user/updatePage", method = RequestMethod.POST)
	public ModelAndView renderUpdatePage(@RequestParam(value="id") int id, RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		mav.setView(new RedirectView("/user/info#update",true));
		User user=service.findOne(id);
		user.setUserId(id);
		rattr.addFlashAttribute("toUpdate",user);
		return mav;
	}
		
	@PostMapping("/user/update")
	public ModelAndView update(@Valid User user,BindingResult results) {
		ModelAndView mav = new ModelAndView();
		if(results.hasErrors()){
			mav.addObject("errorList",createErrorList(results));
			mav.setView(new RedirectView("/user/info#update",true));
		}else{
			service.update(user);
			mav.addObject("success","User details updates succesfully.");
			mav.addObject("user",user);
			mav.addObject("carList",carService.findAll());
			mav.setViewName("userinfo");
		}
		return mav;
	}
	
	@PostMapping("/user/delete")
	public ModelAndView deleteUser(@RequestParam("userId") int userId) {
		ModelAndView mav = new ModelAndView("user_signup");
		service.delete(userId);
		mav.addObject("success","User deleted sucessfully.");
		return mav;
	}
	
    @PostMapping("/user/carselection")
    public ModelAndView carSelection(@RequestParam int userId, @RequestParam int carId) throws ProhibitedOperationException
    {
    	ModelAndView mav = new ModelAndView("userinfo");
    	User user=service.findOne(userId);
        Car car=carService.findOne(carId);
        if(service.findCarMapping(user.getUserName(),car.getCarId())){
			throw new ProhibitedOperationException(ErrorCode.ONEUSERPERCAR.getDescription(),ErrorCode.ONEUSERPERCAR);
		}else if(carService.findUserMapping(user.getUserId())){
			throw new ProhibitedOperationException(ErrorCode.ALREADYINUSE.getDescription(),ErrorCode.ALREADYINUSE);
		}else if(user.getWallet()<car.getCost()){
        	throw new ProhibitedOperationException(ErrorCode.INSUFFICIENTCASH.getDescription(),ErrorCode.INSUFFICIENTCASH);
        }else{
			service.selectCar(user, car);
			mav.addObject("user",user);
			mav.addObject("carList",carService.findAll());
	        mav.addObject("success","You have rented car:"+car.getLicensePlate()+" succesfully");
		}
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
