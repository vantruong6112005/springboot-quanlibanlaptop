/*
 * @ (#) AuthenticationImpl.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.dto.request.AuthenticationRequest;
import vantruong.iuh.exception.UserNotFoundException;
import vantruong.iuh.repository.UserRepository;
import vantruong.iuh.service.AuthenticationService;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl  implements AuthenticationService {
    private final   UserRepository userRepository;
    @Override
    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UserNotFoundException()
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        //return

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
