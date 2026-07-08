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
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthenticationServiceImpl  implements AuthenticationService {
    private final   UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;

@Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            var verified=signedJWT.verify(verifier);
            // kiểm tra token hết hạn hay chưa
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            return IntrospectResponse.builder()
                    .valid(verified && expirationTime.after(new Date()))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }


}


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UserNotFoundException()
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        //return

        boolean isMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new UnauthenticatedException();

        }
        var token = generateToken(request.getUsername());

       return AuthenticationResponse.builder()
               .token(token)
               .authenticated(true).build();
    }


    private String generateToken(String username){
        JWSHeader header = new JWSHeader( (JWSAlgorithm.HS512));

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("dev")
                .issueTime(new Date())
                .expirationTime(
                        new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())
                )
                .claim("customeClaim","Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject= new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }
}

