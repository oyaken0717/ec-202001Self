package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminContorller {

	@Autowired
	private AdminService adminService;
	
	/**
	 * 全ユーザーの注文一覧画面
	 * 
	 * @param model　全ユーザーの注文(Orderのstatusが1or2)情報が入ったList
	 * @return 注文一覧画面
	 */
	@RequestMapping("/to-order-list")
	public String toOrderList(Model model) {
		List<Order> orderList = adminService.findAll();
		model.addAttribute("orderList",orderList);
		return "order_list";
	}
}
