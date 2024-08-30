package com.vendor.service;

import java.util.List;

import com.vendor.model.Cart;

public interface CartService {
	
	public Cart saveCart(Integer productId,Integer userId);
	
	public List<Cart> getCartByUser(Integer userId);

}
