package com.vendor.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String category(Model m) {
		m.addAttribute("categories", categoryService.getAllCategory());

		return "admin/add-category";
	}

	@PostMapping("/savecategory")
	public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
		category.setImagename(imageName);

		Boolean existCategory = categoryService.existCategory(category.getName());
		if (existCategory) {
			session.setAttribute("errorMsg", "Categry Already exist");
		} else {
			Category savedCategory = categoryService.saveCategory(category);
			if (ObjectUtils.isEmpty(savedCategory)) {
				session.setAttribute("errorMsg", "Category not saved. Internal server error");
			} else {
				// Define the path where you want to save the file
				String uploadDir = "uploads/img/category_img";
				File uploadDirectory = new File(uploadDir);

				// Create directories if they don't exist
				if (!uploadDirectory.exists()) {
					uploadDirectory.mkdirs();
				}

				// Save the file
				Path filePath = Paths.get(uploadDirectory.getAbsolutePath(), file.getOriginalFilename());
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("File saved to: " + filePath);

				session.setAttribute("successMsg", "Category Saved Successfully");
			}
		}

		return "redirect:/admin/category";
	}
	
	
	/* Delete Categpry */
	
	
}
