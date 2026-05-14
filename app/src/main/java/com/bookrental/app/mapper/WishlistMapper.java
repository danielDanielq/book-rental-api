package com.bookrental.app.mapper;

import com.bookrental.app.dto.wishlistdto.WishlistRequest;
import com.bookrental.app.dto.wishlistdto.WishlistSimpleResponse;
import com.bookrental.app.entity.Wishlist;

public class WishlistMapper {
    public static WishlistSimpleResponse toSimpleResponse(Wishlist wishlist){
        WishlistSimpleResponse wishlistSimpleResponse = new WishlistSimpleResponse();
        wishlistSimpleResponse.setId(wishlist.getId());
        wishlistSimpleResponse.setAddedDate(wishlist.getAddedDate());
        wishlistSimpleResponse.setUser(UserMapper.toSimpleResponse(wishlist.getUser()));
        wishlistSimpleResponse.setBook(BookMapper.toSimpleResponse(wishlist.getBook()));
        return wishlistSimpleResponse;
    }

    public static Wishlist toEntity(WishlistRequest wishlistRequest){
        Wishlist wishlist = new Wishlist();
        wishlist.setAddedDate(wishlistRequest.getAddedDate());

        return wishlist;
    }
}
