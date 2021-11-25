package com.techlead.booksystem.booksystem.services;

import com.techlead.booksystem.booksystem.dto.UserDTO;
import com.techlead.booksystem.booksystem.dto.UserInputDTO;
import com.techlead.booksystem.booksystem.entities.Role;
import com.techlead.booksystem.booksystem.entities.User;
import com.techlead.booksystem.booksystem.repositories.RoleRepository;
import com.techlead.booksystem.booksystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return user;
    }

    public UserDTO save(UserInputDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        Role role = roleRepository.getById(2L);
        user.getRoles().add(role);
        return new UserDTO(userRepository.save(user));
    }
}
