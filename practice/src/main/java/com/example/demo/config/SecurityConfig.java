package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
				.loginPage("/admin/signin") // ログインページ
				.permitAll()
		        .failureUrl("/admin/signin?error") // ログイン失敗時
		        
				.and()
				.logout()
				.permitAll();

		return http.build();
	}
}
