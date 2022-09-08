package com.example.planner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRequestDTO {
    @Min(0)
    private long id;
    @NotNull
    private User user;
    @NotNull
    private Station station;
    @NotNull
    private Instant time;
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
