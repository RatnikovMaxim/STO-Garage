package com.example.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationRequestDTO {
    @Min(0)
    private long id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    private List<Long> serviceIds;
}
