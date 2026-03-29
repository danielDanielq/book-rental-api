package com.bookrental.app.mappers;

import com.bookrental.app.dtos.AccountRegistration;
import com.bookrental.app.dtos.user.CreateUser;
import com.bookrental.app.dtos.user.UserWithAddress;
import com.bookrental.app.entities.User;
import com.bookrental.app.enums.AccountRole;

public class UserMapper {
    public static User toUser(CreateUser createUser) {
        User user = new User();
        user.setEmail(createUser.getUserData().getEmail());
        user.setFirstName(createUser.getUserData().getFirstName());
        user.setLastName(createUser.getUserData().getLastName());
        user.setAddress(AddressMapper.toAddress(createUser.getUserData().getAddress()));
        return user;
    }

    public static UserWithAddress toUserWithAddress(User user) {
        UserWithAddress userWithAddress = new UserWithAddress();
        userWithAddress.setId(user.getId());
        userWithAddress.setEmail(user.getEmail());
        userWithAddress.setFirstName(user.getFirstName());
        userWithAddress.setLastName(user.getLastName());
        userWithAddress.setAddress(AddressMapper.toSimpleAddress(user.getAddress()));
        return userWithAddress;
    }

    public static AccountRegistration toAccountRegistration(User user,String password) {
        AccountRegistration accountRegistration = new AccountRegistration();
        accountRegistration.setEmail(user.getEmail());
        accountRegistration.setFirstname(user.getFirstName());
        accountRegistration.setLastname(user.getLastName());
        accountRegistration.setPassword(password);
        accountRegistration.setRole(AccountRole.USER);
        return accountRegistration;
    }
}
