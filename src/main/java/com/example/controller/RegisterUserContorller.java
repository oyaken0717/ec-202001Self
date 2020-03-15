package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register-user")
public class RegisterUserContorller {
	
	@RequestMapping("/to-register")
	public String toRegister() {
		return "register_user";
	}
}
