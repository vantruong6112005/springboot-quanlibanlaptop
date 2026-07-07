/*
 * @ (#) Authentication.java     1.0    7/7/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

/*
 * @description
 * @author:NguyenTruong
 * @date:  7/7/2026
 * @version:    1.0
 */

@Data
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;

}
