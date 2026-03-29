package com.bookrental.app.mappers;

import com.bookrental.app.dtos.address.SimpleAddress;
import com.bookrental.app.entities.Address;

public class AddressMapper {
    public static Address toAddress(SimpleAddress address) {
        Address newAddress = new Address();
        newAddress.setStreet(address.getStreet());
        newAddress.setCity(address.getCity());
        newAddress.setCountry(address.getCountry());
        newAddress.setNumber(address.getNumber());
        return newAddress;
    }

    public static SimpleAddress toSimpleAddress(Address address) {
        SimpleAddress newAddress = new SimpleAddress();
        newAddress.setStreet(address.getStreet());
        newAddress.setCity(address.getCity());
        newAddress.setCountry(address.getCountry());
        newAddress.setNumber(address.getNumber());
        return newAddress;
    }
}
