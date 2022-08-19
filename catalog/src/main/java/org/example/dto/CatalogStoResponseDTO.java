package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
@AllArgsConstructor
@Data
public class CatalogStoResponseDTO {
    private long id;
    private String name;
    private String address;
    private String operating_mode;
    private String services;
    private String telephone;
    private String photo;
}
