package com.vendor.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
import com.vendor.model.Product;
import com.vendor.service.CategoryService;
import com.vendor.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String index() {

		return "admin/index";
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
	
	//Get All categories in Dropdown
	@GetMapping("/loadProducts")
	public String addProducts(Model m) {
	List<Category> categories=	categoryService.getAllCategory();
	m.addAttribute("categories",categories);

		return "admin/add-products";
	}
	
	//Save Product through Form
	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

	    // Check if a file is provided
	    if (!file.isEmpty()) {
	        // Set the file name as the image name
	        String imageName = file.getOriginalFilename();
	        product.setImageName(imageName);  // Assuming you have an imageName field in your Product entity

	        // Define the path where you want to save the file
	        String uploadDir = "uploads/img/product_img";
	        File uploadDirectory = new File(uploadDir);

	        // Create directories if they don't exist
	        if (!uploadDirectory.exists()) {
	            uploadDirectory.mkdirs();
	        }

	        // Save the file
	        Path filePath = Paths.get(uploadDirectory.getAbsolutePath(), file.getOriginalFilename());
	        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	        System.out.println("File saved to: " + filePath);
	    } else {
	        product.setImageName("default.jpg");  // Handle the case where no file is uploaded
	    }

	    // Save the product
	    Product savedProduct = productService.saveProduct(product);

	    if (!ObjectUtils.isEmpty(savedProduct)) {
	        session.setAttribute("successMsg", "Product Added Successfully");
	    } else {
	        session.setAttribute("errorMsg", "Unable to add product");
	    }

	    return "redirect:/admin/loadProducts";
	}
	
	//View All products in Admin page
	@GetMapping("/viewproducts")
	public String viewProducts(Model m) {
		m.addAttribute("products", productService.getAllproducts());
		return "admin/products";
	}
	
	//deleting a product
	@GetMapping("/deleteproduct/{id}")
	public String deleteproduct(@PathVariable int id,HttpSession session) {
		Boolean deleteproduct= productService.deleteProduct(id);
		if (deleteproduct) {
			session.setAttribute("successMsg", "Product Delete Successfully");
		} else {
			session.setAttribute("errorMsg", "Unable to delete product");

		}
		return "redirect:/admin/viewproducts";
	}
	
	//load edit page
	
	@GetMapping("/loadeditproduct/{id}")
	public String loadEditproduct(@PathVariable int id,Model m)
	{	
		m.addAttribute("product",productService.getProductById(id));
		m.addAttribute("category",categoryService.getAllCategory());
		return "admin/edit-product";
	}

	


}
