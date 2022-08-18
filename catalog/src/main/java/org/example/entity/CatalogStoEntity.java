package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "catalogs_sto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CatalogStoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String operating_mode;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String services;
    @Column(nullable = false, columnDefinition = "TEXT")
    private long telephone;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String photo;

}
