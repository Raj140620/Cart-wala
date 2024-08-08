package com.vendor.service;

import java.util.List;

import com.vendor.model.Category;


public interface CategoryService {
	
	public Category saveCategory(Category category);
	
	public Boolean existCategory(String name);
	
	public List<Category> getAllCategory();

	

}
