package com.example.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Admin admin = adminRepository.findByEmail(email);
		System.out.println("Admin found: " + (admin != null ? admin.getEmail() : "null")); // デバッグ
		if (admin == null) {
			throw new UsernameNotFoundException("ユーザーが見つかりません: " + email);
		}
		return new User(admin.getEmail(), admin.getPassword(), Collections.emptyList());
	}
}