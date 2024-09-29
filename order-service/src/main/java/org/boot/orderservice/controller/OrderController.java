package org.boot.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.dto.OrderResponseDto;
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
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody OrderRequestDto orderRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

}
