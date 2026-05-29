package com.bookrental.app.dto.userdto;

import com.bookrental.app.dto.addressdto.AddressDto;
import com.bookrental.app.dto.rentaldto.RentalSimpleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UserDetailedResponse { // Note: we want this response for specifically 1 user not on all N users so we don't attract N + 1 querry. Ex: GET /users/5
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
    private List<RentalSimpleResponse> rentals = new ArrayList<>();
}
