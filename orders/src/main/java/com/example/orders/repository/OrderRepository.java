package com.example.orders.repository;

import com.example.orders.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStationIdAndCreatedBetween(long stationId, Instant start, Instant finish);
    List<OrderEntity> findTop5ByNotifiedIsFalseAndStatusOrderById(String status);

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity e SET e.notified = TRUE WHERE e.id = :id")
    void markNotified(final long id);
}
