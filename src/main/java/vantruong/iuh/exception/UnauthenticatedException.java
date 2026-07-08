package vantruong.iuh.exception;

public class UnauthenticatedException extends AppException {
    public UnauthenticatedException() {
        super(ErrorCode.UNAUTHENTICATED);
    }
}
