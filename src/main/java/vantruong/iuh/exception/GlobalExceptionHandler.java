/*
 * @ (#) GlobalExceptionHandler.java     1.0    6/16/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */
package vantruong.iuh.exception;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vantruong.iuh.dto.response.ApiResponse;

/*
 * @description
 * @author:NguyenTruong
 * @date:  6/16/2026
 * @version:    1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        BaseErrorCode errorCode = exception.getErrorCode();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handlingException(Exception exception) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Internal server error: " + exception.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(apiResponse);
    }
}
