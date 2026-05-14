package com.bookrental.app.dto.rentaldto;

import com.bookrental.app.dto.validation.LogicCheck;
import com.bookrental.app.dto.validation.NullCheck;
import com.bookrental.app.dto.validation.ValidDateRange;
import com.bookrental.app.enums.RentalStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter @NoArgsConstructor
@ValidDateRange(groups = LogicCheck.class)
public class RentalRequest {
    @NotNull(groups = NullCheck.class)
    @FutureOrPresent(groups = LogicCheck.class)
    private LocalDate startDate;

    @NotNull(groups = NullCheck.class)
    @FutureOrPresent(groups = LogicCheck.class)
    private LocalDate endDate;

    private RentalStatus rentalStatus;
}
