package com.bookrental.app.mappers;

import com.bookrental.app.dtos.AccountRegistration;
import com.bookrental.app.dtos.librarian.CreateLibrarian;
import com.bookrental.app.dtos.librarian.SimpleLibrarian;
import com.bookrental.app.entities.Librarian;
import com.bookrental.app.enums.AccountRole;

public class LibrarianMapper {
    public static Librarian toLibrarian(CreateLibrarian createLibrarian) {
        Librarian librarian = new Librarian();
        librarian.setEmail(createLibrarian.getLibrarianData().getEmail());
        librarian.setFirstName(createLibrarian.getLibrarianData().getFirstName());
        librarian.setLastName(createLibrarian.getLibrarianData().getLastName());
        return librarian;
    }

    public static Librarian toLibrarian(SimpleLibrarian simpleLibrarian) {
        Librarian librarian = new Librarian();
        librarian.setEmail(simpleLibrarian.getEmail());
        librarian.setFirstName(simpleLibrarian.getFirstName());
        librarian.setLastName(simpleLibrarian.getLastName());
        return librarian;
    }

    public static SimpleLibrarian toSimpleLibrarian(Librarian librarian){
        SimpleLibrarian simpleLibrarian = new SimpleLibrarian();
        simpleLibrarian.setId(librarian.getId());
        simpleLibrarian.setEmail(librarian.getEmail());
        simpleLibrarian.setFirstName(librarian.getFirstName());
        simpleLibrarian.setLastName(librarian.getLastName());
        return simpleLibrarian;
    }

    public static AccountRegistration toAccountRegistration(Librarian librarian,String password) {
        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setEmail(librarian.getEmail());
        accountRegistration.setFirstname(librarian.getFirstName());
        accountRegistration.setLastname(librarian.getLastName());
        accountRegistration.setRole(AccountRole.LIBRARIAN);
        accountRegistration.setPassword(password);
        return accountRegistration;
    }
}
