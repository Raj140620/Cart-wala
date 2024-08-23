package com.vendor.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendor.model.UserData;
import com.vendor.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void getUserDetails(Principal p,Model m) {
		
		if (p!=null) {
			String email=p.getName();
			UserData userData=userService.getUserByEmail(email);
			m.addAttribute("user", userData);
		}
		
	}
	
	@GetMapping("/")
	public String home() {
		
		return "user/home";
	}

}
