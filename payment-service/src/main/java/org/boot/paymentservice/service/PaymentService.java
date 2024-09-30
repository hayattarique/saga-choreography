package org.boot.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.constant.PaymentStatus;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.dto.PaymentRequestDto;
import org.boot.commondtos.event.OrderEvent;
import org.boot.commondtos.event.PaymentEvent;
import org.boot.paymentservice.entities.UserTransaction;
import org.boot.paymentservice.repository.UserBalanceRepository;
import org.boot.paymentservice.repository.UserTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserBalanceRepository balanceRepository;
    private final UserTransactionRepository transactionRepository;

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentDto = new
                PaymentRequestDto(orderDto.getOrderId(), orderDto.getUserId(), orderDto.getAmount());
        return balanceRepository.findById(orderDto.getUserId())
                .filter(user -> user.getBalance() > paymentDto.getAmount())
                .map(user -> {
                    user.setBalance(user.getBalance() - paymentDto.getAmount());
                    transactionRepository.save(new UserTransaction(orderDto.getOrderId(), orderDto.getUserId(), paymentDto.getAmount()));
                    return new PaymentEvent(PaymentStatus.PAYMENT_COMPLETED, paymentDto);
                }).orElse(new PaymentEvent(PaymentStatus.PAYMENT_FAILED, paymentDto));


    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        transactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(transaction -> {
                    transactionRepository.delete(transaction);
                    transactionRepository.findById(transaction.getUserId())
                            .ifPresent(user->user.setAmount(user.getAmount()+transaction.getAmount()));
                });
    }
}
