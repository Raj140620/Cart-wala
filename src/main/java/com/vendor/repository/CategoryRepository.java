package com.vendor.repository;

import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.vendor.model.Category;
import com.vendor.model.UserData;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
	
	public Boolean existsByName(String name);

	public List<Category> findByIsActiveTrue();
	
	   // Find all categories stored by a specific user
    List<Category> findByStoredBy(UserData user);
}
