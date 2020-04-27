package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザー登録をする時のコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/register-user")
public class RegisterUserContorller {

	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	@Autowired
	private RegisterUserService registerUserService;

	/**
	 * ユーザー登録画面へ
	 * 
	 * @return ユーザー登録画面
	 */
	@RequestMapping("/to-register")
	public String toRegister() {
		return "register_user";
	}

	/**
	 * ユーザーを登録する
	 * 
	 * @param form 入力されたユーザー情報が入ったオブジェクト
	 * @return ログイン画面
	 */
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm form, BindingResult result) {
		if (result.hasErrors()) {
			return toRegister();
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		registerUserService.insert(user);
		return "redirect:/login-user/to-login";
	}
}
