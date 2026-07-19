package vantruong.iuh.service;

import org.springframework.data.jpa.repository.JpaRepository;
import vantruong.iuh.dto.request.UserCreationRequest;
import vantruong.iuh.dto.request.UserUpdateRequest;
import vantruong.iuh.dto.response.UserResponse;
import vantruong.iuh.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);


    UserResponse getCurrentUser();
}
