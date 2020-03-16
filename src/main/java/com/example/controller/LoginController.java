package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログインする時のコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/login-user")
public class LoginController {
	
	/**
	 * ログイン画面へ
	 * @return ログイン画面
	 */
	@RequestMapping("/to-login")
	public String toLogin(Model model,@RequestParam(required = false) String error) {
	//■「@RequestParam(required = false) String error」
	//> リクエストパラメータ(error)が何も入力されなかった場合 > 値にnullが設定されます。	
		if (error != null) {
			model.addAttribute("errorMessage","メールアドレス、またはパスワードが間違っています");
		}
		return "login";
	}

	/**
	 * ログアウトをする
	 * @return 商品一覧画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		return "item_list_noodle2";
	}
}