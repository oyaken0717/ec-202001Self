package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.LoginUserRepository;

@Service
public class UDSImplement implements UserDetailsService {
//	public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private LoginUserRepository loginUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = loginUserRepository.findByEmail(email);
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
		if(user.isAdmin()) {
			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
		}
		return new LoginUser(user,authorityList);
	}
}
