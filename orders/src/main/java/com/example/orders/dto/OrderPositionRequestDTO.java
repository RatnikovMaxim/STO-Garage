package com.example.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Data
public class OrderPositionRequestDTO {
    @Min(0)
    private long id;
    private long serviceId;
    private long price;
}
