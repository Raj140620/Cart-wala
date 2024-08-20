package com.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vendor.model.Product;
import com.vendor.repository.ProductRepository;
import com.vendor.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {

		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllproducts() {

		return productRepository.findAll();
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		Product product = productRepository.findById(id).orElse(null);
		if (!ObjectUtils.isEmpty(product)) {
			productRepository.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public Product getProductById(Integer id) {

		Product product = productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public List<Product> getAllActiveProducts(String category) {
		List<Product> products=null;
		if (ObjectUtils.isEmpty(category)) {
			products= productRepository.findByIsActiveTrue();
		} else {
			products= productRepository.findByCategory(category);

		}
		return products;
	}

}
