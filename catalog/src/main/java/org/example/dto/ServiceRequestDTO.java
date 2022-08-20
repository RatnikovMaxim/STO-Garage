package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ServiceEntity;
import org.example.repository.ServiceRepository;

import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceRequestDTO {
    @Min(0)
    private long id;
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
}
