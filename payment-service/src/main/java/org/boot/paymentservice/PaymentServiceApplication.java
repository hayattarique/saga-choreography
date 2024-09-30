package org.boot.paymentservice;

import lombok.RequiredArgsConstructor;
import org.boot.paymentservice.entities.UserBalance;
import org.boot.paymentservice.repository.UserBalanceRepository;
import org.boot.paymentservice.service.PaymentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentServiceApplication {
    private final UserBalanceRepository balanceRepository;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    //dumping dummy data into user UserBalance
    @Bean
    public CommandLineRunner runner() {
        return arg -> {
            List<UserBalance> userList = Stream.of(new UserBalance(101, 12000),
                    new UserBalance(102, 20000),
                    new UserBalance(103, 20000),
                    new UserBalance(104, 25000)).toList();
            balanceRepository.saveAll(userList);
        };
    }
}
