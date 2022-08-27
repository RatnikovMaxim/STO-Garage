package com.example.orders.repository;

import com.example.orders.entity.OrderPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPositionRepository extends JpaRepository<OrderPositionEntity, Long> {
}
