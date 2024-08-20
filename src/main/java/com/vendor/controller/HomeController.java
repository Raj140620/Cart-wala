package com.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vendor.model.Category;
import com.vendor.model.Product;
import com.vendor.service.CategoryService;
import com.vendor.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public String index() {
		
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@GetMapping("/products")
	public String products(Model m) {
		List<Category> categories=categoryService.getAllActiveCategory();
		
		List<Product> products=productService.getAllActiveProducts();
		m.addAttribute("products",products);
		m.addAttribute("categories",categories);
		return "products";
	}
	@GetMapping("/product")
	public String detail() {
		
		return "view_product";
	}

}
