package com.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendor.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
