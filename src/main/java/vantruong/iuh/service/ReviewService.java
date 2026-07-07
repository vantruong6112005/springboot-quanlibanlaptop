package vantruong.iuh.service;

import vantruong.iuh.dto.request.ReviewRequest;
import vantruong.iuh.dto.response.ReviewResponse;
import java.util.List;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request);
    List<ReviewResponse> getReviewsByProductId(Long productId);
    void deleteReview(Long id);
}
