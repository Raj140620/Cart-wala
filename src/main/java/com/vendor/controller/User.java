package com.vendor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user")
public class User {
	
	@GetMapping("/")
	public String home() {
		return "user/home";
	}
	

}
