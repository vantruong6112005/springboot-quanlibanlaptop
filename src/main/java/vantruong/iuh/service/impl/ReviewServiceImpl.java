package vantruong.iuh.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.dto.request.ReviewRequest;
import vantruong.iuh.dto.response.ReviewResponse;
import vantruong.iuh.entity.Product;
import vantruong.iuh.entity.Review;
import vantruong.iuh.entity.User;
import vantruong.iuh.exception.AppException;
import vantruong.iuh.exception.ProductNotFoundException;
import vantruong.iuh.exception.ReviewNotFoundException;
import vantruong.iuh.exception.UserNotFoundException;
import vantruong.iuh.mapper.ReviewMapper;
import vantruong.iuh.repository.ProductRepository;
import vantruong.iuh.repository.ReviewRepository;
import vantruong.iuh.repository.UserRepository;
import vantruong.iuh.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException());

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
        return reviewRepository.findByProductId(productId).stream()
                .map(reviewMapper::toReviewResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException();
        }
        reviewRepository.deleteById(id);
    }
}
