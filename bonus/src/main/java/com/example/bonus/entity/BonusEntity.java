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

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    @AttributeOverride(name = "name", column = @Column(name = "user_name"))
    private UserEmbedded user;


    private long orderId;

    private int bonusSum;

    private Instant created;

    private Instant validTo;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserEmbedded {
        private long id;
        private String name;
    }
}
