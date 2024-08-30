package com.vendor.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vendor.model.Cart;
import com.vendor.model.UserData;
import com.vendor.service.CartService;
import com.vendor.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@ModelAttribute
	public void getUserDetails(Principal p,Model m) {
		
		if (p!=null) {
			String email=p.getName();
			UserData userData=userService.getUserByEmail(email);
			m.addAttribute("user", userData);
		}
		
	}
	
	@GetMapping("/")
	public String home() {
		
		return "user/home";
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

}
