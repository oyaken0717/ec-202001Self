package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.AddShoppingCartForm;

@Controller
@RequestMapping("/cart")
public class CartContorller {

	@RequestMapping("/show-cart-list")
	public String showCartList(AddShoppingCartForm form) {
		
		
		return "cart_list";
	}
}
