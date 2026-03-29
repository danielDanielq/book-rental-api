package com.bookrental.app.dtos.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SimpleAddress {
    @NotBlank(message = "country must be given")
    private String country;
    @NotBlank(message = "city must be given")
    private String city;
    @NotBlank(message = "street must be given")
    private String street;
    @NotBlank(message = "number must be given")
    private String number;
    ;
}
