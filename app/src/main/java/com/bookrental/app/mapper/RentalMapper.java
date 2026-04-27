package com.bookrental.app.mapper;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.rentaldto.RentalRequest;
import com.bookrental.app.dto.rentaldto.RentalSimpleResponse;
import com.bookrental.app.dto.userdto.UserSimpleResponse;
import com.bookrental.app.entity.Rental;

public class RentalMapper {
    public static RentalSimpleResponse toSimpleResponse(Rental rental) {
        RentalSimpleResponse rentalSimpleResponse = new RentalSimpleResponse();
        rentalSimpleResponse.setId(rental.getId());
        rentalSimpleResponse.setStartDate(rental.getStartDate());
        rentalSimpleResponse.setEndDate(rental.getEndDate());
        rentalSimpleResponse.setReturnDate(rental.getReturnDate());
        rentalSimpleResponse.setRentalStatus(rental.getRentalStatus());

        if (rental.getUser() != null) {
            rentalSimpleResponse.setUser(UserMapper.toSimpleResponse(rental.getUser()));
        }

        if (rental.getExampler() != null && rental.getExampler().getBook() != null) {
            rentalSimpleResponse.setBook(BookMapper.toSimpleResponse(rental.getExampler().getBook()));
        }

        if (rental.getExampler() != null && rental.getExampler().getLibrary() != null) {
            rentalSimpleResponse.setLibrary(LibraryMapper.toSimpleResponse(rental.getExampler().getLibrary()));
        }

        return rentalSimpleResponse;
    }

    public static Rental toEntity(RentalRequest rentalRequest) {
        Rental rental = new Rental();
        rental.setStartDate(rentalRequest.getStartDate());
        rental.setEndDate(rentalRequest.getEndDate());
        rental.setRentalStatus(rentalRequest.getRentalStatus());

        return  rental;
    }
}
