package com.techlead.booksystem.booksystem.repositories;

import com.techlead.booksystem.booksystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
