package com.example.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationResponseDTO {
    private long id;
    private String name;
    private List<Service> services;
    @AllArgsConstructor
    @Data
    public static class Service {
        private long id;
        private String name;
    }
}
