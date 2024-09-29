package org.boot.orderservice.service;

import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.orderservice.entities.PurchaseOrder;

import java.util.List;

public interface OrderService {
    List<PurchaseOrder> getOrders();

    PurchaseOrder createOrder(OrderRequestDto order);

    void cancelOrder(OrderRequestDto order);
}
