package vantruong.iuh.exception;

public class BrandNotFoundException extends AppException {
    public BrandNotFoundException() {

        super(ErrorCode.BRAND_NOT_FOUND);
    }
}
