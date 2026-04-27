package com.bookrental.app.dto.rentaldto;

import com.bookrental.app.enums.RentalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor
public class RentalRequest {
    @NotNull(message = "This field can't be empty")
    private LocalDate startDate;

    @NotNull(message = "This field can't be empty")
    private LocalDate endDate;

    private RentalStatus rentalStatus;
}
