package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public void saveAdmin(AdminForm adminForm) {
		Admin admin = new Admin();
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(passwordEncoder.encode(adminForm.getPassword())); // パスワードハッシュ化
		adminRepository.save(admin);
	}

	@Override
	public boolean validateAdmin(String email, String password) {
		Admin admin = adminRepository.findByEmail(email);
		if (admin != null) {
			return passwordEncoder.matches(password, admin.getPassword()); // パスワードの照合
		}
		return false;
	}
}
