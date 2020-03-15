package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;

/**
 * ユーザー登録をする時のコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/register-user")
public class RegisterUserContorller {
	
	/**
	 * ユーザー登録画面へ
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/to-register")
	public String toRegister() {
		return "register_user";
	}
	
	@RequestMapping("/register")
	public String register(RegisterUserForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		System.out.println(user.toString());
		return "register_user";
	}
}
