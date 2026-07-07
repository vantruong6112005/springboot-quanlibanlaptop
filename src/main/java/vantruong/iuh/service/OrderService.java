package vantruong.iuh.service;

import vantruong.iuh.dto.request.OrderRequest;
import vantruong.iuh.dto.response.OrderResponse;
import vantruong.iuh.entity.OrderStatus;
import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Long id);
    List<OrderResponse> getOrdersByUserId(Long userId);
    OrderResponse updateOrderStatus(Long id, OrderStatus status);
    void cancelOrder(Long id);
}
