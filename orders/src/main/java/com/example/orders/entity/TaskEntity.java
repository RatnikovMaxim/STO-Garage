package com.example.orders.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "tasks")
@Entity
public class TaskEntity {
  @Id
  @Column(columnDefinition = "TEXT")
  private String id;
  @Column(columnDefinition = "TEXT", nullable = false)
  private String data;
  @Column(nullable = false)
  private boolean sent;
  @Column(nullable = false)
  private boolean sentError;
  @Column(columnDefinition = "TIMESTAMPTZ", nullable = false, updatable = false)
  private Instant created;
}
