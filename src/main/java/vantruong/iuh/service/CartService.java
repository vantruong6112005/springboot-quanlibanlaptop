package vantruong.iuh.service;

import vantruong.iuh.dto.request.CartItemRequest;
import vantruong.iuh.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartByUserId(Long userId);
    CartResponse addItemToCart(Long userId, CartItemRequest request);
    CartResponse removeItemFromCart(Long userId, Long productId);
    CartResponse updateItemQuantity(Long userId, Long productId, Integer quantity);
}
