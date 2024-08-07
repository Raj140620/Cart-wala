package com.vendor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/")
	public String index() {
		
		return "admin/index";
	}
	@GetMapping("/addProducts")
	public String addProducts() {
		
		return "admin/add-products";
	}
	@GetMapping("/addCategory")
	public String addCategory() {
		
		return "admin/add-category";
	}

}
