package com.bookrental.app.service;

import com.bookrental.app.repository.ExamplerRepository;
import com.bookrental.app.repository.RentalRepository;
import com.bookrental.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExamplerRepository examplerRepository;

    @InjectMocks
    private RentalService rentalService;

    @Test
    void searchRentals_withAllNullFilters_doesNotThrowNullPointerException() {
        when(rentalRepository.findAll(any(Example.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        assertDoesNotThrow(() ->
                rentalService.searchRentals(null, null, null, null, null, null, null, 0, 10, "id")
        );
    }
}
