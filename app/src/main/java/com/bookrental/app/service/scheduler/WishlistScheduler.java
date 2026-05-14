package com.bookrental.app.service.scheduler;

import com.bookrental.app.entity.User;
import com.bookrental.app.entity.Wishlist;
import com.bookrental.app.exception.ResouceNotFoundException;
import com.bookrental.app.repository.UserRepository;
import com.bookrental.app.repository.WishlistRepository;
import com.bookrental.app.security.SecurityConfig;
import com.bookrental.app.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistScheduler {
    private final WishlistRepository wishlistRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public WishlistScheduler(WishlistRepository wishlistRepository, EmailService emailService, UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 10 1 * * ?")
    public void searchForAll7daysOldWishlist() {
        Long userId = SecurityConfig.getcurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User with id " + userId + " not found")
        );

        List<Wishlist> userWishlist = wishlistRepository.findWishlist7daysOldAndUserId(LocalDate.now().minusDays(7), userId);

        List<String> bookTitles = new ArrayList<>();
        List<LocalDate> addedDates = new ArrayList<>();

        for (Wishlist wishlist : userWishlist) {
            bookTitles.add(wishlist.getBook().getTitle());
            addedDates.add(wishlist.getAddedDate());
        }

        String to = user.getEmail();
        String userName = user.getFirstName();

        String text = String.format("Salut %s,\n\nWislist-ul tau arata cam asa: %s.\nDatele la care au fost adaugate fiind %s.\nO zi bună!",
                userName, bookTitles, addedDates);

        emailService.sendEmail(
                to,
                "Your book wishlist is 7 days old!",
                text
        );
    }


}
