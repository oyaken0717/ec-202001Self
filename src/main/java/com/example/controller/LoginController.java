package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.LoginUserForm;

/**
 * ログインする時のコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("")
public class LoginController {
	
	/**
	 * ログイン画面へ
	 * @return ログイン画面
	 */
	@RequestMapping("/to-login")
	public String toLogin() {
		return "login";
	}

	/**
	 * ログイン認証をする
	 * @return 商品一覧画面
	 */
	@RequestMapping("/login")
	public String login(LoginUserForm form) {
		
		return "item_list_noodle";
	}


}