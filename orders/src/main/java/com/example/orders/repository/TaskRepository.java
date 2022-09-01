package com.example.orders.repository;

import com.example.orders.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends JpaRepository<TaskEntity, String> {
  @Modifying
  @Transactional
  @Query("UPDATE TaskEntity e SET e.sent = TRUE WHERE e.id = :id")
  void markSent(final String id);

  @Modifying
  @Transactional
  @Query("UPDATE TaskEntity e SET e.sentError = TRUE WHERE e.id = :id")
  void markSentError(final String id);
}
