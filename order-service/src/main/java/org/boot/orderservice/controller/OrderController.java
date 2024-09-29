package org.boot.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.orderservice.entities.PurchaseOrder;
import org.boot.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<PurchaseOrder> addOrder(@RequestBody OrderRequestDto orderRequest) {
        PurchaseOrder purchaseOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseOrder);
    }

    @GetMapping("/get")
    public ResponseEntity<List<PurchaseOrder>> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

}
