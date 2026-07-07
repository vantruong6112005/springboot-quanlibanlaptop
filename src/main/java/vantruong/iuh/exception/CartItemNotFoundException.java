package vantruong.iuh.exception;

public class CartItemNotFoundException extends AppException {
    public CartItemNotFoundException() {
        super(ErrorCode.CART_ITEM_NOT_FOUND);
    }
}
