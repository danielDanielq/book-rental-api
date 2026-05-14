package com.bookrental.app.service;

import com.bookrental.app.dto.rentaldto.RentalSimpleResponse;
import com.bookrental.app.entity.*;
import com.bookrental.app.enums.RentalStatus;
import com.bookrental.app.exception.*;
import com.bookrental.app.mapper.RentalMapper;
import com.bookrental.app.repository.*;
import com.bookrental.app.security.SecurityConfig;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final ExamplerRepository examplerRepository;

    public RentalService(
            RentalRepository rentalRepository,
            UserRepository userRepository,
            ExamplerRepository examplerRepository) {

        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.examplerRepository = examplerRepository;
    }

    @Transactional
    public RentalSimpleResponse createRental(
            Long bookId,
            Long libraryId,
            LocalDate requestedStartDate,
            LocalDate requestedEndDate) {


//        if (requestedStartDate.isBefore(LocalDate.now())) {
//            throw new DateOutOfBoundsException("The introduced dates are out of bounds");
//        }

        if (requestedEndDate.isBefore(requestedStartDate) || requestedEndDate.isEqual(requestedStartDate)) {
            throw new DateOutOfBoundsException("The introduced dates are out of bounds");
        }

        Long userId = SecurityConfig.getcurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User with id " + userId + " not found")
        );

        List<Exampler> examplersToRent = examplerRepository.findAvailableExamplersForDates(
                bookId,
                libraryId,
                requestedStartDate,
                requestedEndDate
        );

        if (examplersToRent.isEmpty()) {
            throw new ResouceNotFoundException("Examplers not found");
        }

        Exampler examplerToRent = examplersToRent.get(0);
        examplerToRent.setUpdateTime(LocalDateTime.now());

        Rental rental = new Rental();
        rental.setStartDate(requestedStartDate);
        rental.setEndDate(requestedEndDate);
        rental.setRentalStatus(RentalStatus.PENDING);
        rental.setUser(user);
        rental.setExampler(examplerToRent);

        Rental savedRental = rentalRepository.save(rental);
        return RentalMapper.toSimpleResponse(savedRental);
    }

    @Transactional
    public RentalSimpleResponse returnExampler(Long rentalId) {
        // Note: using .findById => this entity becomes "managed" by hibernate, he will implicitly compare and if anything is changed he will UPDATE the DB => no need to update or .save explicitly;
        Rental rentalToFind = rentalRepository.findById(rentalId).orElseThrow(
                () -> new ResouceNotFoundException("Rental with id " + rentalId + " not found")
        );

        Long userId = SecurityConfig.getcurrentUserId();

        if(!(rentalToFind.getUser().getId().equals(userId))) {
            throw new AccessDeniedException("Not allowed to return");
        }

        if (rentalToFind.getRentalStatus().equals(RentalStatus.FINISHED)) {
            throw new BookAlreadyReturnedException("Book already returned");
        }

        rentalToFind.setRentalStatus(RentalStatus.FINISHED);
        rentalToFind.setReturnDate(LocalDate.now()); // Note: setting the returned date of this exampler

        return RentalMapper.toSimpleResponse(rentalToFind);
    }

    public Page<RentalSimpleResponse> searchRentals(LocalDate startDate, LocalDate endDate, LocalDate returnDate, RentalStatus rentalStatus, String userEmail, String bookTitle, String libraryName, int page, int size, String sort) {
        Rental probeRental = new Rental();

        probeRental.setStartDate(startDate);
        probeRental.setEndDate(endDate);
        probeRental.setReturnDate(returnDate);
        probeRental.setRentalStatus(rentalStatus);
        probeRental.getUser().setEmail(userEmail);
        probeRental.getExampler().getBook().setTitle(bookTitle);
        probeRental.getExampler().getLibrary().setName(libraryName);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Rental> example = Example.of(probeRental, matcher   );
        Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
        Page<Rental> rentalPage = rentalRepository.findAll(example, pageRequest);

        Page<RentalSimpleResponse> responsePage = rentalPage.map(p -> RentalMapper.toSimpleResponse(p));
        return responsePage;
    }

    @Transactional
    public RentalSimpleResponse updateRentalStatus(Long rentalId, RentalStatus targetStatus) {
        Rental rentalToFind = rentalRepository.findById(rentalId).orElseThrow(
                () -> new ResouceNotFoundException("Rental with id " + rentalId + " not found")
        );

        RentalStatus currentStatus = rentalToFind.getRentalStatus();
        if (!currentStatus.isNextStatePossible(targetStatus)) {
            throw new NotAllowedToUpdateException("Not allowed to update (end or forbidden state machine)");
        }

        rentalToFind.setRentalStatus(targetStatus);
        if(targetStatus.equals(RentalStatus.FINISHED)) {
            rentalToFind.setReturnDate(LocalDate.now());
        }

        return RentalMapper.toSimpleResponse(rentalToFind);
    }


}
