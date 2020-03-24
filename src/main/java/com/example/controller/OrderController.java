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
	
	/**
	 * 注文確認画面にいく.
	 * 
	 * @param loginUser ログインユーザー情報
	 * @param model モデル
	 * @return 注文確認画面へ
	 */
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
	
	/**
	 * 注文をする.
	 * 
	 * @return toOrderFinish()メソッドへ
	 */
	@RequestMapping("/decide")
	public String decide() {
		return "redirect:/order/to-order-finish";
	}
	
	/**
	 * 注文完了画面へ
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/to-order-finish")
	public String toOrderFinish() {
		return "order_finished";
	}
}
