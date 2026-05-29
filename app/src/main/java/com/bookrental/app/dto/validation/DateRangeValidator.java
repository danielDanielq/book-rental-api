package com.bookrental.app.dto.validation;

import com.bookrental.app.dto.rentaldto.RentalRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, RentalRequest> {
    @Override
    public boolean isValid(RentalRequest value, ConstraintValidatorContext context) {
        LocalDate startDate = value.getStartDate();
        LocalDate endDate = value.getEndDate();

        if (startDate == null || endDate == null) {
            return true;
        }

        return endDate.isAfter(startDate);
    }
}
