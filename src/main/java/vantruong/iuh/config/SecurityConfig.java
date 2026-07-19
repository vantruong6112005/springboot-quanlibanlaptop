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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import vantruong.iuh.entity.RoleName;

import javax.crypto.spec.SecretKeySpec;

import static org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration.jwtDecoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final String[] AUTH_WHITELIST = {
            "/users/**" ,
            "/auth/login",
            "/auth/introspect"
            // Cho phép truy cập vào các endpoint /auth mà không cần xác thực
            // Cho phép truy cập vào các endpoint /users mà không cần xác thực
    };
    @Value("${jwt.signerKey}")
    private String singerKey;
    // cấu hình security filter chain để xác thực và ủy quyền các yêu cầu HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, AUTH_WHITELIST).permitAll()
//                .requestMatchers(HttpMethod.GET,"/user").hasAuthority(RoleName.ROLE_ADMIN.name())// Cho phép truy cập vào các endpoint được liệt kê mà không cần xác thực
                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các yêu cầu khác
        );
        http.oauth2ResourceServer(oauth2->

                oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        .decoder(jwtDecorder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));


        http.csrf(AbstractHttpConfigurer::disable);
         // Vô hiệu hóa CSRF (Cross-Site Request Forgery) để đơn giản hóa việc kiểm tra
        return http.build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
         JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
         jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
         JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
         jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
         return jwtAuthenticationConverter;

    }
    @Bean
    JwtDecoder jwtDecorder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(singerKey.getBytes(),"HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }
}