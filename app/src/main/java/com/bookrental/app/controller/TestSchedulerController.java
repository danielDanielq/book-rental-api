package com.bookrental.app.controller;

import com.bookrental.app.service.scheduler.RentalScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSchedulerController {

    private final RentalScheduler rentalScheduler;

    public TestSchedulerController(RentalScheduler rentalScheduler) {
        this.rentalScheduler = rentalScheduler;
    }

    @GetMapping("/test-scheduler")
    public void testScheduler() {
        rentalScheduler.searchForAllPendingRentals();
    }

}
