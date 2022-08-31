package com.example.id.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Builder // lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity // аннотации hibernate
@Table(name = "tokens")
public class TokenEntity {
    @Id
    private String token;

    @ManyToOne(optional = false)
    private UserEntity user;

    @Column( // поле представленнное колонкой в бд
            insertable = true,
            updatable = false,
            nullable = false,
            columnDefinition = "TIMESTAMPTZ NOT NULL"
    )
    private Instant expire;

    @Column(
            insertable = false,
            updatable = false,
            nullable = false,
            columnDefinition = "TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP"
    )
    private Instant created;
}
