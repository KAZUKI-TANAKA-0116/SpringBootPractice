package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.requestMatchers("/admin/signup", "/admin/signin").permitAll() // signupとsigninへのアクセスを許可
				.anyRequest().authenticated() // 他のURLは用認証
				.and()
				.formLogin()
				.loginPage("/admin/signin") // カスタムのログインページ
				.defaultSuccessUrl("/admin/contacts") // ログイン成功
				.failureUrl("/admin/signin?error=true") // ログイン失敗
				.permitAll()
				.and()
				.logout()
				.permitAll();

		return http.build();
	}

	// パスワードエンコーダーのBean定義
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
