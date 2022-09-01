package com.example.bonus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "bonuses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BonusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long clientId;

    private String clientName;

    private long orderId;

    private int bonusSum;

    private Instant created;

    private Instant validTo;
}
