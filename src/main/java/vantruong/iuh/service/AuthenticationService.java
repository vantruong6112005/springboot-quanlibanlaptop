/*
 * @ (#) AuthenticationService.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.service;

import com.nimbusds.jose.JOSEException;
import vantruong.iuh.dto.request.AuthenticationRequest;
import vantruong.iuh.dto.request.IntrospectRequest;
import vantruong.iuh.dto.response.AuthenticationResponse;
import vantruong.iuh.dto.response.IntrospectResponse;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException;
}
