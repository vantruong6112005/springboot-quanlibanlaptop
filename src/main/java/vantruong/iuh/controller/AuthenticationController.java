/*
 * @ (#) AuthenticationController.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.controller;


import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vantruong.iuh.dto.request.AuthenticationRequest;
import vantruong.iuh.dto.response.ApiResponse;
import vantruong.iuh.dto.response.AuthenticationResponse;
import vantruong.iuh.service.AuthenticationService;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        // Implementation for login logic
        boolean isAuthenticated = authenticationService.authenticate(request);
        AuthenticationResponse response = AuthenticationResponse.builder()
                .authenticated(isAuthenticated)
                .build();
        return ApiResponse.<AuthenticationResponse>builder()
                // có thể có code 
                .message(isAuthenticated ? "Login successful" : "Invalid credentials")
                .data(response)
                .build();
    }
}
