package com.vendor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vendor.model.Cart;
import com.vendor.model.Product;
import com.vendor.model.UserData;
import com.vendor.repository.CartRepository;
import com.vendor.repository.ProductRepository;
import com.vendor.repository.UserRepository;
import com.vendor.service.CartService;


@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	
	@Override
	public Cart saveCart(Integer productId, Integer userId) {
		
		UserData userData = userRepository.findById(userId).get();
		Product product = productRepository.findById(productId).get();
		
		Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);	
		
		Cart cart=null;
		
		if (ObjectUtils.isEmpty(cartStatus)) {
			cart=new Cart();
			cart.setUser(userData);
			cart.setProduct(product);
			cart.setQuantity(1);
			cart.setTotalPrice(1*product.getDiscountedPrice());
		}else {
			cart=cartStatus;
			cart.setQuantity(cart.getQuantity()+1);
			cart.setTotalPrice(cart.getQuantity()*cart.getProduct().getDiscountedPrice());
		}
		
		Cart saveCart= cartRepository.save(cart);
		
		return saveCart;
	}

	@Override
	public List<Cart> getCartByUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
