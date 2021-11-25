package com.techlead.booksystem.booksystem.dto;

import com.techlead.booksystem.booksystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserInputDTO {
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String password;

    public UserInputDTO(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
    }
}
