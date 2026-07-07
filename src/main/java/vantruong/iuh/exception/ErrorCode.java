package vantruong.iuh.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import vantruong.iuh.exception.BaseErrorCode;

@Getter
public enum ErrorCode implements BaseErrorCode {
    BAD_REQUEST("error.bad_request", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("error.unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("error.forbidden", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED("error.unauthenticated", HttpStatus.UNAUTHORIZED),

    INVALID_NUMBER_FORMAT("error.invalid_number_format", HttpStatus.BAD_REQUEST),
    GENERATE_TOKEN_FAILED("error.generate_token_failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INTROSPECT_FAILED("error.introspect_failed", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATE_USERNAME("error.duplicate_username", HttpStatus.CONFLICT),
    DUPLICATE_EMAIL("error.duplicate_email", HttpStatus.CONFLICT),
    DUPLICATE_PHONE("error.duplicate_phone", HttpStatus.CONFLICT),
    BAD_CREDENTIALS("error.bad_credentials", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("error.token_expired", HttpStatus.UNAUTHORIZED),
    NOTIFICATION_NOT_FOUND("error.notification_not_found", HttpStatus.NOT_FOUND),
    HASHTAG_NOT_FOUND("error.hashtag_not_found", HttpStatus.NOT_FOUND),
    MENTIONED_USER_NOT_FOUND("error.mentioned_user_not_found", HttpStatus.NOT_FOUND),
    POST_ACCESS_DENIED("error.post_access_denied", HttpStatus.FORBIDDEN),
    POST_NOT_FOUND("error.post_not_found", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND("error.brand_not_found", HttpStatus.NOT_FOUND),
    CONFLICT_USERNAME("error.conflict_username", HttpStatus.CONFLICT),
    UPLOAD_MEDIA_FAILED("error.upload_media_failed", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_FOUND("error.file_not_found", HttpStatus.NOT_FOUND),
    FILE_SIZE_EXCEEDED("error.file_size_exceeded", HttpStatus.BAD_REQUEST),
    INVALID_FILE_TYPE("error.invalid_file_type", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND("error.category_not_found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("error.user_not_found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND("error.product_not_found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("error.order_not_found", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND("error.cart_not_found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND("error.cart_item_not_found", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND("error.review_not_found", HttpStatus.NOT_FOUND);
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}