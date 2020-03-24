package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.CartForm;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

@Controller
@RequestMapping("/cart")
public class CartContorller {
	
	@Autowired
	private OrderService cartService;

	@Autowired
	private HttpSession session;

	// ■@AuthenticationPrincipal
	// 大前提：LoginUserのデータ > Principal(メールアドレス)オブジェクトと繋がってる。
	// Principalオブジェクトと繋がったUserを引っ張ってくる。(UDSが情報をモデルに入れておいてくれてるからできる。)
	@RequestMapping("/add-cart")
	public String addCart(CartForm form, @AuthenticationPrincipal LoginUser loginUser) {
		// ■ログインしていない場合 > セッション自体のIDをuserIdにいれとく
		// ※文字列化している > hashCode()で数値化する。
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			// ■ログインしている。 > そこからuserのIDを取ってくる。
			userId = loginUser.getUser().getId();
		}
		cartService.insert(form, userId);
		return "redirect:/cart/show-cart-list";
	}

	/**
	 * カートの中身を表示する.
	 * 
	 * @return カートの中身一覧画面
	 */
	@RequestMapping("/show-cart-list")
	public String showCartList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		Order order = cartService.showCartList(userId, 0);
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("order", order);
		return "cart_list";
	}
	
	@RequestMapping("/delte-order-item")
	public String delteOrderItem(int orderItemId) {
		cartService.delteOrderItem(orderItemId);
		return "redirect:/cart/show-cart-list";
	}
}
