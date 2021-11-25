package com.techlead.booksystem.booksystem.services;

import com.techlead.booksystem.booksystem.entities.User;
import com.techlead.booksystem.booksystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizationService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User authenticated(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username);
        } catch (Exception e) {
//            throw new UnauthorizedException("Invalid user");
        }
        return null;
    }

    public void validateSelfOrAdmin(Long userId) {
        User user = authenticated();
        if (! user.getId().equals(userId) && ! user.hasHole("ROLE_ADMIN")) {
//            throw new ForbiddenException("Access denied");
        }
    }
}
