package com.vendor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendor.model.Category;
import com.vendor.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public String index() {
		
		return "admin/index";
	}
	@GetMapping("/loadProducts")
	public String addProducts() {
		
		return "admin/add-products";
	}
	@GetMapping("/category")
	public String addCategory() {
		
		return "admin/add-category";
	}
	
	@PostMapping("/savecategory")
	public String saveCategory(@ModelAttribute Category category,HttpSession session) {
		
		
		
		
		
		
		categoryService.saveCategory(category);
		
		return "redirect:/category";
	}
}
