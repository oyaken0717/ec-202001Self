package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.service.CartService;

/**
 * カートに入れた注文情報どうのこうのする.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private HttpSession session;
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/to-order-confirm")
	public String toOrderConfirm(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		//■引数の「0」はstatus		
		Order order = cartService.showCartList(userId, 0);
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("order", order);
		return "order_confirm";
	}
}
