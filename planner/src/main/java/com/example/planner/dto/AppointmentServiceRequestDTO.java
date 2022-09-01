package com.example.planner.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Data
public class AppointmentServiceRequestDTO {
    @Min(0)
    private long id;
    private long serviceId;
    private String serviceName;
}
