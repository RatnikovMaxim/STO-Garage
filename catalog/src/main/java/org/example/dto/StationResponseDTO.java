package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
