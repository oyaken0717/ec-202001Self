package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.form.CartForm;
import com.example.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartContorller {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private CartService cartService;
	
	//■@AuthenticationPrincipal
	// 大前提：LoginUserのデータ > Principalオブジェクトに入ってる。
	//  Principalオブジェクト内のLoginUserを引っ張ってくる。
	@RequestMapping("/add-cart")
	public String addCart(CartForm form, @AuthenticationPrincipal LoginUser loginUser) {
		//■ログインしていない場合 > セッション自体のIDをuserIdにいれとく
		//　※文字列化している > hashCode()で数値化する。		
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			//■ログインしている。 > そこからuserのIDを取ってくる。			
			userId = loginUser.getUser().getId();
		}
		cartService.insert(form, userId);
		return "redirect:/cart/show-cart-list";
	}

	@RequestMapping("/show-cart-list")
	public String showCartList(CartForm form, @AuthenticationPrincipal LoginUser loginUser) {
		return "cart_list";
	}
}
