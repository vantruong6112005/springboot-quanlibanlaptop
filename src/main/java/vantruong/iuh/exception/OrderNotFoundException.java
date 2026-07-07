package vantruong.iuh.exception;

public class OrderNotFoundException extends AppException {
    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND);
    }
}
