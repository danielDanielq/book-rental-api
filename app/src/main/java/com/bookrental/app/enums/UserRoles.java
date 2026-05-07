package com.bookrental.app.enums;

public enum UserRoles {
    CLIENT("user"),
    ADMIN("admin"),
    LIBRARIAN("librarian");

    private final String type;

    UserRoles(String type) {
        this.type = type;
    }

    public String getType() {return type;}
}
