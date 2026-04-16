package com.bookrental.app.mapper;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.rentaldto.RentalSimpleResponse;
import com.bookrental.app.entity.Rental;

public class RentalMapper {
    public static RentalSimpleResponse toSimpleDTO(Rental rental) {
        RentalSimpleResponse rentalSimpleResponse = new RentalSimpleResponse();
        rentalSimpleResponse.setId(rental.getId());
        rentalSimpleResponse.setRentalDate(rental.getRentalDate());
        rentalSimpleResponse.setDueDate(rental.getDueDate());
        rentalSimpleResponse.setReturnDate(rental.getReturnDate());
        rentalSimpleResponse.setStatus(rental.getStatus());
        rentalSimpleResponse.setBook(BookMapper.toSimpleDTO(rental.getBook()));

        return rentalSimpleResponse;
    }
}
