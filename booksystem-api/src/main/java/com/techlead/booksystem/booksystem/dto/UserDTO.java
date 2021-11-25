package com.techlead.booksystem.booksystem.dto;


import com.techlead.booksystem.booksystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDTO {
    private Long id;
    private String username;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
    }
}
