package com.bookrental.app.enums;

public enum Role {
    CLIENT("user"),
    ADMIN("admin"),
    LIBRARIAN("librarian");

    private final String type;

    Role(String type) {
        this.type = type;
    }

    public String getType() {return type;}
}
