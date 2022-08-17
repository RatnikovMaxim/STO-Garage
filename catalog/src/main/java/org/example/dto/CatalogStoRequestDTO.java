package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatalogStoRequestDTO {
    @Min(0)
    private long id;

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    @NotBlank
    @Size(min = 10, max = 60)
    private String address;

    @NotNull
    @Size(min = 10, max = 20)
    private String operating_mode;

    @NotBlank
    @Size(min = 10, max = 60)
    private String services;

    @Pattern(regexp = "^(\\+ \\d{1}( )?)?(\\ d{3}[ ]?)(\\ d{2}[ ]?){2}\\ d{2}$")
    private long telephone;

    @NotBlank
    private String photo;
}
