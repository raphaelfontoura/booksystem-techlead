package com.techlead.booksystem.booksystem.repositories;

import com.techlead.booksystem.booksystem.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
