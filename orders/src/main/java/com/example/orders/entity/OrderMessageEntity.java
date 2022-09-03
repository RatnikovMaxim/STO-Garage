package com.example.orders.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "orders")
@Entity
public class OrderMessageEntity {
  @Id
  @Column(columnDefinition = "TEXT")
  private String id;
//  @Column(columnDefinition = "TEXT", nullable = false)
//  private String data;
@Embedded
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@AttributeOverride(name = "name", column = @Column(name = "user_name"))
private OrderEntity.UserEmbedded user;
  @Embedded
  @AttributeOverride(name = "id", column = @Column(name = "station_id"))
  @AttributeOverride(name = "name", column = @Column(name = "station_name"))
  private OrderEntity.StationEmbedded station;
  @OneToMany(mappedBy = "order")
  private List<OrderPositionEntity> positions;
  @Column(nullable = false, columnDefinition = "TEXT")
  private String status;
  @Column(nullable = false)
  private boolean sent;
  @Column(nullable = false)
  private boolean sentError;
  @Column(columnDefinition = "TIMESTAMPTZ", nullable = false, updatable = false)
  private Instant created;

  @Embeddable
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class UserEmbedded {
    private long id;
    private String name;
  }

  @Embeddable
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class StationEmbedded {
    private long id;
    private String name;
  }
}
