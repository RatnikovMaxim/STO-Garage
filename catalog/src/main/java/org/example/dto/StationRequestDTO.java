package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationRequestDTO {
    @Min(0)
    private long id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    @NotNull
    @Size(min = 1)
    private List<@NotNull @Pattern(regexp = "[0-9]") Long> serviceIds;
}
