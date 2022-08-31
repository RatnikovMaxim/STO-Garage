package com.example.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentResponseDTO {
    private long id;
    private User user;
    private Station station;
    private List<AppointmentService> services;
    private String status;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class User {
        private long id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Station {
        private long id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class AppointmentService {
        private long id;
        private Service service;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class Service {
            private long serviceId;
            private String serviceName;
        }
    }
}
