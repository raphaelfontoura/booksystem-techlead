package com.techlead.booksystem.booksystem.factory;

import com.techlead.booksystem.booksystem.dto.UserDTO;
import com.techlead.booksystem.booksystem.entities.Role;
import com.techlead.booksystem.booksystem.entities.User;

public class UserFactory {

    public static User getUser() {
        Role role = new Role();
        role.setId(2L);
        role.setAuthority("ROLE_USER");
        User user = new User(2L, "user", "password");
        user.getRoles().add(role);
        return user;
    }

    public static User getAdmin() {
        Role role = new Role();
        role.setId(1L);
        role.setAuthority("ROLE_ADMIN");
        User user = new User(1L, "admin", "password");
        user.getRoles().add(role);
        return user;
    }

    public static UserDTO getUserDTO() {
        return new UserDTO(getUser());
    }
}
