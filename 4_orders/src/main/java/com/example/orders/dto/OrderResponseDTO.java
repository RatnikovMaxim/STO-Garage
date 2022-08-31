package com.example.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDTO {
    private long id;
    private User user;
    private Station station;
    private List<OrderPosition> positions;
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
    public static class OrderPosition {
        private long id;
        private Service service;
        private long price;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class Service {
            private long serviceId;
            private String serviceName;
        }
    }
}
