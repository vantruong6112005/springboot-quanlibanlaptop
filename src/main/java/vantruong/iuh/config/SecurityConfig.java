/*
 * @ (#) SecurityConfig.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.config;


/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */
// vao spring security=>servlet application==>architecture


import org.hibernate.boot.internal.Abstract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] AUTH_WHITELIST = {
            "/auth/login", // Cho phép truy cập vào các endpoint /auth mà không cần xác thực
            "/users",
            "/users/**" ,
            "/auth/introspect" // Cho phép truy cập vào các endpoint /auth mà không cần xác thực
            // Cho phép truy cập vào các endpoint /users mà không cần xác thực
    };
    // cấu hình security filter chain để xác thực và ủy quyền các yêu cầu HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_WHITELIST).permitAll() // Cho phép truy cập vào các endpoint được liệt kê mà không cần xác thực
                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các yêu cầu khác
        );
        http.csrf(AbstractHttpConfigurer::disable);
         // Vô hiệu hóa CSRF (Cross-Site Request Forgery) để đơn giản hóa việc kiểm tra
        return http.build();
    }

}