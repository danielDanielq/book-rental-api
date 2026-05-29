package com.bookrental.app.dto.addressdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AddressDto {
    @NotBlank(message = "This field can't be empty")
    private String country;
    @NotBlank(message = "This field can't be empty")
    private String city;
    @NotBlank(message = "This field can't be empty")
    private String street;
    @NotBlank(message = "This field can't be empty")
    private String buildingNumber;
}
