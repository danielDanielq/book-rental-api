package com.bookrental.app.dto.addressdto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AddressSimpleResponse {
    private String country;
    private String city;
    private String street;
    private String buildingNumber;
}
