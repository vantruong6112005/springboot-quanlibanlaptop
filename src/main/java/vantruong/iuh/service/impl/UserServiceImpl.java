package vantruong.iuh.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.dto.request.UserCreationRequest;
import vantruong.iuh.dto.request.UserUpdateRequest;
import vantruong.iuh.dto.response.UserResponse;
import vantruong.iuh.entity.Role;
import vantruong.iuh.entity.RoleName;
import vantruong.iuh.entity.User;
import vantruong.iuh.exception.AppException;
import vantruong.iuh.exception.ErrorCode;
import vantruong.iuh.exception.UserNotFoundException;
import vantruong.iuh.mapper.UserMapper;
import vantruong.iuh.repository.RoleRepository;
import vantruong.iuh.repository.UserRepository;
import vantruong.iuh.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.DUPLICATE_USERNAME);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = userMapper.toUser(request);

        // Assign default role ROLE_CUSTOMER
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(RoleName.ROLE_CUSTOMER);
                    return roleRepository.save(role);
                });
        roles.add(defaultRole);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail()) 
                && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.DUPLICATE_EMAIL);
        }

        userMapper.updateUser(user, request);

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String rName : request.getRoles()) {
                try {
                    RoleName roleName = RoleName.valueOf(rName);
                    Role role = roleRepository.findByName(roleName)
                            .orElseGet(() -> {
                                Role newRole = new Role();
                                newRole.setName(roleName);
                                return roleRepository.save(newRole);
                            });
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new AppException("Invalid role name: " + rName);
                }
            }
            user.setRoles(roles);
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
