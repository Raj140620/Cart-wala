package com.vendor.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vendor.model.Cart;
import com.vendor.model.OrderRequest;
import com.vendor.model.UserData;
import com.vendor.service.CartService;
import com.vendor.service.OrderService;
import com.vendor.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@ModelAttribute
	public void getUserDetails(Principal p,Model m) {
		
		if (p!=null) {
			String email=p.getName();
			UserData userData=userService.getUserByEmail(email);
			Integer cartCount = cartService.getCartCount(userData.getId());
			m.addAttribute("user", userData);
			m.addAttribute("cartCount", cartCount);
		}
	}
	
	@GetMapping("/")
	public String home() {
		
		return "user/home";
	}
	@GetMapping("/carts")
	public String cart() {
		
		return "user/cart";
	}
	
	
	@GetMapping("/addtocart")
	public String addToCart(@RequestParam Integer pid,@RequestParam Integer uid,HttpSession session) {
		
		Cart saveCart = cartService.saveCart(pid, uid);
		
		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "unable to add product to your cart");
		}
		else {
			session.setAttribute("successMsg", "Item Saved to Cart Successfully");
		}
		return "redirect:/product/"+pid;
	}
	
	private UserData getLoggedInUserData(Principal p) {
		String email=p.getName();
		UserData userByEmail = userService.getUserByEmail(email);
		return userByEmail;
	}
	
	@GetMapping("/cart")
	public String ShowCart(Principal p, Model m) {
	    UserData user = getLoggedInUserData(p);
	    List<Cart> cartItems = cartService.getCartByUser(user.getId());
	    
	    // Add cart items to the model
	    m.addAttribute("cartItems", cartItems);
	    if (cartItems.size()>0) {
			
		
	    // Calculate the total ordered amount
	    Double totalOrderdAmount = cartItems.get(cartItems.size() - 1).getTotalOrderdAmount();
	    m.addAttribute("totalOrderdAmount", totalOrderdAmount);
	    
	    // Calculate and add the total number of items to the model
	    int totalItems = cartItems.size();
	    m.addAttribute("totalItems", totalItems);
	    }
	    else {
		    m.addAttribute("msg","No items in your cart ");

		}
	    return "user/cart";
	   
	}
	
	@GetMapping("/cartQtyUpdate")
	public String updateCartQty(@RequestParam String sy,@RequestParam Integer cid) {
		cartService.updateQty(sy,cid);
		return "redirect:/user/cart";
	}
	
	@GetMapping("/order")
	public String orderUi() {
		
		return "user/order";
	}
	@PostMapping("/saveorder")
	public String saveOrder(@ModelAttribute OrderRequest request,Principal p) {
		
		UserData user=getLoggedInUserData(p);
		orderService.saveOrder(user.getId(), request);

		
		return "/user/paymentSuccess";
	}

	

}
