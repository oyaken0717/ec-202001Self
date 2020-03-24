package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.service.OrderService;

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
	private OrderService orderService;
	
	@RequestMapping("/to-order-confirm")
	public String toOrderConfirm() {
//		Order order=orderService.findByUserIdAndStatus(userId,status);
//		Order order = orderService.
		return "order_confirm";
	}
}
