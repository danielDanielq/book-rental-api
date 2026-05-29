package com.bookrental.app.dto.wishlistdto;

import com.bookrental.app.dto.bookdto.BookSimpleResponse;
import com.bookrental.app.dto.userdto.UserSimpleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class WishlistSimpleResponse {
    private Long id;
    private LocalDate addedDate;
    private UserSimpleResponse user;
    private BookSimpleResponse book;
}
