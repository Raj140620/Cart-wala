package com.vendor.service;

import java.util.List;

import com.vendor.model.Product;

public interface ProductService {
	
	 public Product saveProduct(Product product);
	 
	 public List<Product>  getAllproducts();
	 
	 public Boolean deleteProduct(Integer id);
	 
	 public Product getProductById(Integer id);
	 
	 public List<Product>  getAllActiveProducts();

}
