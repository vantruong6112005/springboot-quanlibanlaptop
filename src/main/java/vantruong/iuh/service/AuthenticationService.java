/*
 * @ (#) AuthenticationService.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.service;

import org.springframework.stereotype.Service;
import vantruong.iuh.dto.request.AuthenticationRequest;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */

public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
}
