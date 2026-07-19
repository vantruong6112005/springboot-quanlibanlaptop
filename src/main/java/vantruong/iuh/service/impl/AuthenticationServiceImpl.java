/*
 * @ (#) AuthenticationImpl.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.service.impl;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.dto.request.AuthenticationRequest;
import vantruong.iuh.dto.request.IntrospectRequest;
import vantruong.iuh.dto.response.AuthenticationResponse;
import vantruong.iuh.dto.response.IntrospectResponse;
import vantruong.iuh.exception.UnauthenticatedException;
import vantruong.iuh.exception.UserNotFoundException;
import vantruong.iuh.repository.UserRepository;
import vantruong.iuh.service.AuthenticationService;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */@Service // Đánh dấu đây là Service của Spring
@RequiredArgsConstructor // Lombok tự tạo constructor cho các final field
@Transactional // Các phương thức sẽ chạy trong transaction
@Slf4j // Tạo đối tượng log
public class AuthenticationServiceImpl implements AuthenticationService {

    // Repository dùng để truy vấn thông tin người dùng trong database
    private final UserRepository userRepository;

    // Đọc khóa bí mật từ file application.properties/application.yml
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

    /**
     * Kiểm tra tính hợp lệ của JWT Token
     */
    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException {

        // Lấy token từ request
        var token = request.getToken();

        // Tạo đối tượng dùng để xác thực chữ ký của JWT
        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());

        try {
            // Chuyển chuỗi token thành đối tượng SignedJWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Kiểm tra chữ ký JWT có hợp lệ hay không
            var verified = signedJWT.verify(verifier);

            // Lấy thời gian hết hạn của token
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            // Token hợp lệ khi:
            // 1. Chữ ký đúng
            // 2. Chưa hết hạn
            return IntrospectResponse.builder()
                    .valid(verified && expirationTime.after(new Date()))
                    .build();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Xác thực đăng nhập
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        // Tìm user theo username
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        // BCrypt dùng để so sánh mật khẩu đã mã hóa
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // So sánh mật khẩu người dùng nhập với mật khẩu trong database
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        // Sai mật khẩu thì ném exception
        if (!isMatch) {
            throw new UnauthenticatedException();
        }

        // Tạo JWT Token nếu đăng nhập thành công
        var token = generateToken(request.getUsername());

        // Trả về kết quả xác thực
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    /**
     * Sinh JWT Token
     */
    private String generateToken(String username) {

        // Khai báo thuật toán ký HS512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Khai báo các thông tin (claims) của JWT
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                // Người sở hữu token
                .subject(username)

                // Đơn vị phát hành token
                .issuer("dev")

                // Thời điểm tạo token
                .issueTime(new Date())

                // Token hết hạn sau 1 giờ
                .expirationTime(
                        new Date(
                                Instant.now()
                                        .plus(1, ChronoUnit.HOURS)
                                        .toEpochMilli()
                        )
                )

                // Custom Claim
                .claim("customeClaim", "Custom")

                .build();

        // Chuyển Claims thành Payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Tạo JWT Object gồm Header và Payload
        JWSObject jwsObject = new JWSObject(header, payload);

        try {

            // Ký JWT bằng khóa bí mật
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));

            // Chuyển JWT thành chuỗi để trả về client
            return jwsObject.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}