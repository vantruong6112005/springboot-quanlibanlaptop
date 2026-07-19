/*
 * @ (#) ApiResponse.java     1.0    6/16/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/*
 * @description
 * @author:NguyenTruong
 * @date:  6/16/2026
 * @version:    1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
//==>Những field null sẽ bị ẩn

public class ApiResponse <T>{
    // code
    private String message;
    private T data;
}
