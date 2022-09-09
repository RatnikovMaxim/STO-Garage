package com.example.bonus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderMessage {
    private long id;
    private User user;
    private Station station;
    private List<Position> positions;
    private String status;
    private Instant created;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class User {
        private long id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Station {
        private long id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Position {
        private long id;
        private Service service;
        private long price;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Data
        public static class Service {
            private long id;
            private String name;
        }
    }
}
