package com.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendor.model.Category;
import com.vendor.repository.CategoryRepository;
import com.vendor.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryrepository;
	
	@Override
	public Category saveCategory(Category category) {
		
		return categoryrepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		
		return categoryrepository.findAll();
	}

	@Override
	public Boolean existCategory(String name) {
		
		return categoryrepository.existsByName(name);
	}

}
