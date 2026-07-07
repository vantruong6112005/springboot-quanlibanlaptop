package vantruong.iuh.exception;

public class CategoryNotFoundException extends AppException {
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
