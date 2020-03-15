package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.RegisterUserRepository;

/**
 * ユーザー情報の登録をするサービス.
 * 
 * @author oyamadakenji
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	public RegisterUserRepository registerUserRepository;
	
	/**
	 * ユーザー情報を登録します。
	 * 
	 * @param user 登録するユーザー情報の入ったオブジェクト
	 */
	public void insert(User user) {
		registerUserRepository.insert(user);
	}
	
}
