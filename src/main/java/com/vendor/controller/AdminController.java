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

	/*
	 * @PostMapping("/updatecategory") public String UpdateCategory(@ModelAttribute
	 * Category category, @RequestParam("file") MultipartFile file,HttpSession
	 * session) {
	 * 
	 * Category oldCategory = categoryService.getCategoryById(category.getId());
	 * String imageName = file.isEmpty() ? oldCategory.getImagename() :
	 * file.getOriginalFilename();
	 * 
	 * if (!ObjectUtils.isEmpty(category)) {
	 * oldCategory.setName(category.getName());
	 * oldCategory.setIsActive(category.getIsActive());
	 * oldCategory.setImagename(imageName); }
	 * 
	 * Category updatedCategory = categoryService.saveCategory(oldCategory);
	 * 
	 * if (!ObjectUtils.isEmpty(updatedCategory)) {
	 * session.setAttribute("successMsg", "Category Updated Successfully");
	 * 
	 * } else { session.setAttribute("errorMsg", "Category not updated");
	 * 
	 * } return "redirect:/admin/loadeditcategory/"+category.getId(); }
	 */
	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {

		Category oldCategory = categoryService.getCategoryById(category.getId());
		String imageName = file.isEmpty() ? oldCategory.getImagename() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImagename(imageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {

			session.setAttribute("successMsg", "Category update success");
		} else {
			session.setAttribute("errorMsg", "something wrong on server");
		}

		return "redirect:/admin/loadeditcategory/" + category.getId();
	}

}
