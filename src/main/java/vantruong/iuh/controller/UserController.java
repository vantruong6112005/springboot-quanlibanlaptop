/*
 * @ (#) UserController.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vantruong.iuh.dto.request.UserCreationRequest;
import vantruong.iuh.dto.request.UserUpdateRequest;
import vantruong.iuh.dto.response.ApiResponse;
import vantruong.iuh.dto.response.UserResponse;
import vantruong.iuh.service.UserService;

import java.util.List;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("User has been created successfully")
                .data(userService.createUser(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .message("Fetched all users successfully")
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .message("Fetched user details successfully")
                .data(userService.getUserById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("User has been updated successfully")
                .data(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .message("User has been deleted successfully")
                .build();
    }
}
