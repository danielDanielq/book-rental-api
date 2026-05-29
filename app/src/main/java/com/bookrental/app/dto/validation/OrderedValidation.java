package com.bookrental.app.dto.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({NullCheck.class, LogicCheck.class})
public interface OrderedValidation {
}
