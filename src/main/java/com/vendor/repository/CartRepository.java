package com.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendor.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>  {
	
	public Cart findByProductIdAndUserId(Integer productId,Integer userId);

}
