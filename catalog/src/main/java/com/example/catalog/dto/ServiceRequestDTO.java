package com.example.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceRequestDTO {
    @Min(0)
    private long id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
}
