package org.boot.orderservice.service;

import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getOrders();

    OrderResponseDto createOrder(OrderRequestDto order);

    void cancelOrder(OrderRequestDto order);
}
