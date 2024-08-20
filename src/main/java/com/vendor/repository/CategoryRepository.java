package com.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendor.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	public Boolean existsByName(String name);

	public List<Category> findByIsActiveTrue();
}
