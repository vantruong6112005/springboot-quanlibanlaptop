package vantruong.iuh.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.iuh.dto.request.CartItemRequest;
import vantruong.iuh.dto.response.CartResponse;
import vantruong.iuh.entity.Cart;
import vantruong.iuh.entity.CartItem;
import vantruong.iuh.entity.Product;
import vantruong.iuh.entity.User;
import vantruong.iuh.exception.AppException;
import vantruong.iuh.exception.CartItemNotFoundException;
import vantruong.iuh.exception.ProductNotFoundException;
import vantruong.iuh.exception.UserNotFoundException;
import vantruong.iuh.mapper.CartMapper;
import vantruong.iuh.repository.CartItemRepository;
import vantruong.iuh.repository.CartRepository;
import vantruong.iuh.repository.ProductRepository;
import vantruong.iuh.repository.UserRepository;
import vantruong.iuh.service.CartService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    private Cart getOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponse addItemToCart(Long userId, CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException());

        if (product.getQuantity() < request.getQuantity()) {
            throw new AppException("Insufficient product quantity in stock");
        }

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (product.getQuantity() < newQuantity) {
                throw new AppException("Insufficient product quantity in stock");
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(cartItem);
            cart.getItems().add(cartItem);
        }

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse removeItemFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new CartItemNotFoundException());

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    @Override
    public CartResponse updateItemQuantity(Long userId, Long productId, Integer quantity) {
        if (quantity <= 0) {
            return removeItemFromCart(userId, productId);
        }

        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());

        if (product.getQuantity() < quantity) {
            throw new AppException("Insufficient product quantity in stock");
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new CartItemNotFoundException());

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }
}
