package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entity.ServiceEntity;

import java.util.List;

@AllArgsConstructor
@Data
public class ServiceResponseDTO {
    private long id;
    private String name;
}
