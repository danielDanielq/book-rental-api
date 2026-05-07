package com.bookrental.app.service.scheduler;

import com.bookrental.app.entity.Exampler;
import com.bookrental.app.entity.Rental;
import com.bookrental.app.enums.RentalStatus;
import com.bookrental.app.repository.ExamplerRepository;
import com.bookrental.app.repository.RentalRepository;
import com.bookrental.app.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalScheduler {

    private final RentalRepository rentalRepository;
    private final EmailService emailService;
    private final ExamplerRepository examplerRepository;

    public RentalScheduler(RentalRepository rentalRepository, EmailService emailService,  ExamplerRepository examplerRepository) {
        this.rentalRepository = rentalRepository;
        this.emailService = emailService;
        this.examplerRepository = examplerRepository;
    }

    @Transactional(readOnly = true) // Note: Hibernate will deactivate dirty-checking so there will be no alterations in the DB (no comparison and less memory allocation);
    @Scheduled(cron = "0 0 1 * * ?")
    public void searchForAllPendingRentals() {
        List<Rental> rentals = rentalRepository.findPendingByStartDateBefore(LocalDate.now().minusDays(1) ,RentalStatus.PENDING);

        for(Rental rental : rentals) {
            String bookTitle = rental.getExampler().getBook().getTitle();
            String libraryName = rental.getExampler().getLibrary().getName();
            String rentalStatus = rental.getRentalStatus().toString();
            String to = rental.getUser().getEmail();
            String userName = rental.getUser().getFirstName();
            LocalDate startDate = rental.getStartDate();
            LocalDate endDate = rental.getEndDate();

            String text = String.format("Salut %s,\n\nRezervarea ta pentru %s de la biblioteca %s are acum statusul: %s.\nData limita %s - %s.\nO zi bună!",
                    userName, bookTitle, libraryName, rentalStatus, startDate, endDate);

            emailService.sendEmail(
                    to,
                    "Book rental is " + rentalStatus + "!",
                    text
                    );
        }
    }

    @Scheduled(cron = "0 5 1 * * ?") // Note: If you have multiple schedulers don't let them start at the same time, deescalate them by some minutes;
    @Transactional
    public void searchForAllPendingRentalsNorPickedUpFor3Days() {
        List<Rental> rentals = rentalRepository.findPendingByStartDateBefore(LocalDate.now().minusDays(3) ,RentalStatus.PENDING);

        for(Rental rental : rentals) {
            rental.setRentalStatus(RentalStatus.CANCELED);

            String bookTitle = rental.getExampler().getBook().getTitle();
            String libraryName = rental.getExampler().getLibrary().getName();
            String rentalStatus = rental.getRentalStatus().toString();
            String to = rental.getUser().getEmail();
            String userName = rental.getUser().getFirstName();
            LocalDate startDate = rental.getStartDate();
            LocalDate endDate = rental.getEndDate();

            // rentalRepository.save(rental); Note: Hibernate will do anyway the update, no need for this, because we have the @Transactional annotation;

            String text = String.format("Salut %s,\n\nRezervarea ta pentru %s de la biblioteca %s are acum statusul: %s.\nData limita %s - %s.\nO zi bună!",
                    userName, bookTitle, libraryName, rentalStatus, startDate, endDate);

            emailService.sendEmail(
                    to,
                    "Book rental is " + rentalStatus + "!",
                    text
            );
        }
    }

    @Scheduled(cron = "0 0 20 * * ?") // Note: If you have multiple schedulers don't let them start at the same time, deescalate them by some minutes;
    @Transactional
    public void searchForAllEndDateToday() {
        List<Rental> rentals = rentalRepository.findInProgressByEndDateBefore(LocalDate.now(), RentalStatus.IN_PROGRESS);

        for(Rental rental : rentals) {
            rental.setRentalStatus(RentalStatus.DELAYED);

            String bookTitle = rental.getExampler().getBook().getTitle();
            String libraryName = rental.getExampler().getLibrary().getName();
            String rentalStatus = rental.getRentalStatus().toString();
            String to = rental.getUser().getEmail();
            String userName = rental.getUser().getFirstName();
            LocalDate startDate = rental.getStartDate();
            LocalDate endDate = rental.getEndDate();

            // rentalRepository.save(rental); Note: Hibernate will do anyway the update, no need for this, because we have the @Transactional annotation;

            String text = String.format("Salut %s,\n\nRezervarea ta pentru %s de la biblioteca %s are acum statusul: %s.\nData limita %s - %s.\nO zi bună!",
                    userName, bookTitle, libraryName, rentalStatus, startDate, endDate);

            emailService.sendEmail(
                    to,
                    "Book rental is " + rentalStatus + "!",
                    text
            );
        }
    }

    @Scheduled(cron = "0 10 20 * * ?") // Note: If you have multiple schedulers don't let them start at the same time, deescalate them by some minutes;
    @Transactional
    public void searchDelayedRentalsOtherwiseCancel() {
        List<Rental> rentals = rentalRepository.findPendingByExactStartDate(LocalDate.now().plusDays(1), RentalStatus.PENDING);

        for(Rental rental : rentals) {
            String bookTitle = rental.getExampler().getBook().getTitle();
            String libraryName = rental.getExampler().getLibrary().getName();
            String rentalStatus = rental.getRentalStatus().toString();
            String to = rental.getUser().getEmail();
            String userName = rental.getUser().getFirstName();
            LocalDate startDate = rental.getStartDate();
            LocalDate endDate = rental.getEndDate();

            Long badExamplerId = rental.getExampler().getId();
            boolean isDelayed = rentalRepository.existsByExamplerIdAndRentalStatus(badExamplerId, RentalStatus.DELAYED);

            if(isDelayed) {
                rental.setRentalStatus(RentalStatus.CANCELED);

                List<Exampler> availableExamplers = examplerRepository.findAvailableExamplersForDates(
                        rental.getExampler().getBook().getId(),
                        rental.getExampler().getLibrary().getId(),
                        rental.getStartDate(),
                        rental.getEndDate());

                // Note: Eliminate the bad exampler from the list;
                availableExamplers.removeIf(e -> e.getId().equals(badExamplerId));

                if(availableExamplers.isEmpty()) {
                    String text = String.format(
                            "Salut %s,\n\nNe cerem scuze, dar din cauza unei returnari intarziate, nu mai avem exemplare din %s...",
                            userName,
                            bookTitle);

                    emailService.sendEmail(
                            to,
                            "Book rental is " + rentalStatus + "!",
                            text
                    );
                } else {
                    rental.setExampler(availableExamplers.get(0));
                    rental.setRentalStatus(RentalStatus.PENDING);
                    String text = String.format(
                            "Salut %s,\n\nAm fost nevoiti sa schimbam exemplarul alocat pentru %s la biblioteca %s.\nData limita %s - %s.\nNoua ta rezervare este activa!",
                            userName,
                            bookTitle,
                            libraryName,
                            startDate,
                            endDate);

                    emailService.sendEmail(
                            to,
                            "Book rental is " + rentalStatus + "!",
                            text);
                }
            }
            // rentalRepository.save(rental); Note: Hibernate will do anyway the update, no need for this, because we have the @Transactional annotation;
        }
    }



}
