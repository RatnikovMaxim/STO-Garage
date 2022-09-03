package com.example.orders.model;

import com.example.orders.dto.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderMessage {
    private String id;
    private OrderMessage.User user;
    private OrderMessage.Station station;
    private List<OrderMessage.OrderPosition> positions;
    private String status;
    private long created;

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
        private OrderResponseDTO.OrderPosition.Service service;
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
