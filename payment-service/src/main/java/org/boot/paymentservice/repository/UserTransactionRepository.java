package org.boot.paymentservice.repository;

import org.boot.paymentservice.entities.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {
}
