package com.bookrental.app.enums;

import lombok.Getter;

@Getter
public enum AccountRole {
    USER("role_user"),
    LIBRARIAN("role_librarian");

    private final String type;

    AccountRole(String type) {
        this.type = type;
    }

}
