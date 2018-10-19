package com.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.model.User;
import com.demo.service.UserService;

/**
 * @author ankidaemon
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	UserService service;

	@RequestMapping(value = { "/", "/home" }, method = { RequestMethod.GET })
	public ModelAndView visitHome() {
		return new ModelAndView("home");
	}

	@PostMapping("/info")
	public ModelAndView visitInfo(@RequestParam(name = "name", defaultValue = "Anonymous User") String name,
			@RequestParam(name = "age", required = false) String age) {
		ModelAndView mav = new ModelAndView("info");
		mav.addObject("name", name);
		if (age != null)
			mav.addObject("age", age);
		return mav;
	}

	@GetMapping("/{userId}")
	public ModelAndView getRecord(@PathVariable("userId") int userId) {
		ModelAndView mav = new ModelAndView("info");
		User u=service.findOne(userId);
		if (u!=null) {
			mav.addObject("user",u);
		}else{
			mav.addObject("error","No User found having eId "+userId);
		}
		return mav;
	}
	
	@PostMapping("/signUp")
	public ModelAndView signUp(User user,RedirectAttributes rattr) {
		ModelAndView mav = new ModelAndView();
		mav.setView(new RedirectView("/info#next",true));
		service.createUser(user);
		rattr.addFlashAttribute("redirectedUser", user);
		return mav;
	}
	
	@GetMapping("/info")
	public ModelAndView visitInfo() {
		return new ModelAndView("info");
	}
	
	@GetMapping("/downTime")
	public ModelAndView downTime() {
		return new ModelAndView("downTime");
	}

}
