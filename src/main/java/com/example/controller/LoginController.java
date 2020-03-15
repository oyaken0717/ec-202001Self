package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String login() {
		return "login";
	}
}
