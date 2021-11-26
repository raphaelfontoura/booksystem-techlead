package com.techlead.booksystem.booksystem.resources;

import com.techlead.booksystem.booksystem.dto.UserDTO;
import com.techlead.booksystem.booksystem.dto.UserInputDTO;
import com.techlead.booksystem.booksystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserInputDTO dto) {
        return ResponseEntity.ok(userService.save(dto));
    }
}
