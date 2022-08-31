package com.example.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Data
public class OrderPositionRequestDTO {
    @Min(0)
    private long id;
    @Min(0)
    private long serviceId;
    @Min(0)
    private long price;
}
