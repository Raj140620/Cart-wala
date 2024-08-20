package com.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendor.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByIsActiveTrue();

	List<Product> findByCategory(String category);

	

}
