package com.bookrental.app.dto.rentaldto;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.librarydto.LibrarySimpleResponse;
import com.bookrental.app.dto.userdto.UserSimpleResponse;
import com.bookrental.app.enums.RentalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class RentalSimpleResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;
    private RentalStatus rentalStatus;
    private UserSimpleResponse user;
    private BookSimpleResponse book;
    private LibrarySimpleResponse library;
}
