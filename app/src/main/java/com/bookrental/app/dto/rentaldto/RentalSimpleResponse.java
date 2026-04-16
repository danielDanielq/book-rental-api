package com.bookrental.app.dto.rentaldto;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class RentalSimpleResponse {
    private Long id;
    private LocalDateTime rentalDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private Status status;
    private BookSimpleResponse book;
}
