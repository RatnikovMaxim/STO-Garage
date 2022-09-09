package com.example.orders.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class OrderRequestDTO {
    @Min(0)
    private long id;
    @NotNull
    private User user;
    @NotNull
    private Station station;
    @NotNull
    private String status;

    @AllArgsConstructor
    @Data
    public static class User {
        private long id;
        private String name;
    }

    @AllArgsConstructor
    @Data
    public static class Station {
        private long id;
        private String name;
    }
}
