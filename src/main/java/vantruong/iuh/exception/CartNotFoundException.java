package vantruong.iuh.exception;

public class CartNotFoundException extends AppException {
    public CartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND);
    }
}
