package com.bookrental.app.enums;

public enum ReviewRating {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5);

    private final Integer type;
    ReviewRating(Integer type) {
        this.type = type;
    }
    public Integer getType() {return type;}
}
