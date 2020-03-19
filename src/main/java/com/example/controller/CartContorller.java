package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.form.CartForm;

@Controller
@RequestMapping("/cart")
public class CartContorller {

	@Autowired
	private HttpSession session;
	
	@RequestMapping("/show-cart-list")
//■@AuthenticationPrincipal
// 大前提：ログイン済みのUserDetailsオブジェクト(LoginUser入ってる。) > Principalオブジェクトに格納される。
// アノテーションで、Authentication.getPrincipal()メソッド > Principalオブジェクト内のLoginUserを引っ張ってくる。
	public String showCartList(CartForm form, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		
		return "cart_list";
	}
}
