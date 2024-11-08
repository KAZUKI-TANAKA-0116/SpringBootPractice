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
            .requestMatchers("/admin/signup").permitAll() // signupへのアクセスを許可
            .requestMatchers("/admin/signin").access("!isAuthenticated()") // 未認証ユーザーのみsigninにアクセス許可
            .anyRequest().authenticated() // 他のURLは認証が必要
            .and()
            .formLogin()
            .loginPage("/admin/signin") // カスタムのログインページ
            .defaultSuccessUrl("/admin/contacts", true) // ログイン成功後のリダイレクト先
            .failureUrl("/admin/signin?error=true") // ログイン失敗時のリダイレクト先
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
