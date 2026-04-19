package com.bookrental.app.mapper;

import com.bookrental.app.dto.addressdto.AddressDto;
import com.bookrental.app.dto.userdto.CreateUserRequest;
import com.bookrental.app.dto.userdto.UserDetailedResponse;
import com.bookrental.app.entity.Address;
import com.bookrental.app.entity.Rental;
import com.bookrental.app.entity.User;
import com.bookrental.app.enums.Role;
import com.bookrental.app.exception.NoAddressInRequestException;

public class UserMapper {
    public static User toEntity(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setRole(Role.CLIENT); // Note: this is important otherwise it will crash because of "nullable = false" from the entity itself and every user to register will be a .CLIENT;

        if (createUserRequest.getAddress() == null) {
            throw new NoAddressInRequestException("Address is null.");
        }

        Address address = new Address();
        address.setCountry(createUserRequest.getAddress().getCountry());
        address.setCity(createUserRequest.getAddress().getCity());
        address.setStreet(createUserRequest.getAddress().getStreet());
        address.setBuildingNumber(createUserRequest.getAddress().getBuildingNumber());

        user.setAddress(address); // Note: this will do "user.address = address" and "address.user = this"
        return user;
    }

    public static UserDetailedResponse toDetailedDTO(User user) {
        UserDetailedResponse userDetailedResponse = new UserDetailedResponse();
        userDetailedResponse.setId(user.getId());
        userDetailedResponse.setFirstName(user.getFirstName());
        userDetailedResponse.setLastName(user.getLastName());
        userDetailedResponse.setEmail(user.getEmail());

        if (user.getAddress() == null) {
            throw new NoAddressInRequestException("Address is null.");
        }

        AddressDto addressDto = new AddressDto();
        addressDto.setCountry(user.getAddress().getCountry());
        addressDto.setCity(user.getAddress().getCity());
        addressDto.setStreet(user.getAddress().getStreet());
        addressDto.setBuildingNumber(user.getAddress().getBuildingNumber());
        userDetailedResponse.setAddress(addressDto);

        for (Rental rental : user.getRentals()) {
            userDetailedResponse.getRentals().add(RentalMapper.toSimpleDTO(rental));
        }

        return userDetailedResponse;
    }
}
