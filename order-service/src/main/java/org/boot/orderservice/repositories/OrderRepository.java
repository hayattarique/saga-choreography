package org.boot.orderservice.repositories;

import org.boot.orderservice.entities.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {



}
