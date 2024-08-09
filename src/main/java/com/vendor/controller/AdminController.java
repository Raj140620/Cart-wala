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
import org.springframework.web.bind.annotation.PathVariable;
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

	/* Delete Category */

	@GetMapping("/deletecategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {

		System.out.println(id);
		Boolean deleteCategory = categoryService.deleteCategory(id);
		if (deleteCategory) {

			session.setAttribute("successMsg", "Category Deleted Successfully");

		} else {
			session.setAttribute("errorMsg", "Unable to Delete Category");

		}

		return "redirect:/admin/category";
	}

	/* Fetch data for Edit Category */
	@GetMapping("/loadeditcategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {

		m.addAttribute("category", categoryService.getCategoryById(id));

		return "admin/edit-category";
	}

	/* Update Category */

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		// Retrieve the old category based on the ID
		Category oldCategory = categoryService.getCategoryById(category.getId());
		
		// Determine the image name to use (either the old one or the new one if a file was uploaded)
		String imageName = file.isEmpty() ? oldCategory.getImagename() : file.getOriginalFilename();
		category.setImagename(imageName);

		if (!ObjectUtils.isEmpty(oldCategory)) {
			// Update the fields of the old category with the new data
			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImagename(imageName);

			// Save the updated category
			Category updatedCategory = categoryService.saveCategory(oldCategory);

			// If a new file was uploaded, save it
			if (!file.isEmpty()) {
				String uploadDir = "uploads/img/category_img";
				File uploadDirectory = new File(uploadDir);

				// Create directories if they don't exist
				if (!uploadDirectory.exists()) {
					uploadDirectory.mkdirs();
				}

				// Save the new image file
				Path filePath = Paths.get(uploadDirectory.getAbsolutePath(), file.getOriginalFilename());
				Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			}

			if (!ObjectUtils.isEmpty(updatedCategory)) {
				session.setAttribute("successMsg", "Category updated successfully");
			} else {
				session.setAttribute("errorMsg", "Failed to update the category. Please try again.");
			}
		} else {
			session.setAttribute("errorMsg", "Category not found.");
		}

		return "redirect:/admin/loadeditcategory/" + category.getId();
	}


}
