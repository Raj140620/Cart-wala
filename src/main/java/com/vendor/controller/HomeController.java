package com.vendor.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vendor.model.Category;
import com.vendor.model.Product;
import com.vendor.model.UserData;
import com.vendor.service.CategoryService;
import com.vendor.service.ProductService;
import com.vendor.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

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
	public String index() {

		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/products")
	public String products(Model m, @RequestParam(value = "category", defaultValue = "") String category) {
		List<Category> categories = categoryService.getAllActiveCategory();

		List<Product> products = productService.getAllActiveProducts(category);
		m.addAttribute("products", products);
		m.addAttribute("categories", categories);
		m.addAttribute("paramValue", category);
		return "products";
	}

	@GetMapping("/product")
	public String detail() {

		return "view_product";
	}

	@PostMapping("/saveuser")
	public String saveUser(@ModelAttribute UserData user, @RequestParam("file") MultipartFile file, HttpSession session)
	        throws IOException {

	    // Check if the file is empty or not
	    String imageName;
	    if (file != null && !file.isEmpty()) {
	        imageName = file.getOriginalFilename();
	    } else {
	        imageName = "default.jpg"; // Use a default image if none is uploaded
	    }
	    user.setImagename(imageName);
	    System.out.println(user);

	    // Save the user
	    UserData savedUser = userService.saveUser(user);

	    if (!ObjectUtils.isEmpty(savedUser)) {
	        // Define the path where you want to save the file
	        String uploadDir = "uploads/img/profile_img";
	        File uploadDirectory = new File(uploadDir);

	        // Create directories if they don't exist
	        if (!uploadDirectory.exists()) {
	            uploadDirectory.mkdirs();
	        }

	        // Save the file only if a file was uploaded
	        if (!file.isEmpty()) {
	            Path filePath = Paths.get(uploadDirectory.getAbsolutePath(), file.getOriginalFilename());
	            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("File saved to: " + filePath);
	        }

	        session.setAttribute("successMsg", "User Registered Successfully");
	    } else {
	        session.setAttribute("errorMsg", "User Registration Failed");
	    }

		return "redirect:/register";
	}

}
