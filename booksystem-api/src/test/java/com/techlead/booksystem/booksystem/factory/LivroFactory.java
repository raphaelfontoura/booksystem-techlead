package com.techlead.booksystem.booksystem.factory;

import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.entities.Livro;
import com.techlead.booksystem.booksystem.entities.User;

import java.time.LocalDate;

public class LivroFactory {

    public static Long existingId = 1L;

    public static Livro getLivro() {
        User user = new User(1L, "user", "password");
        Livro livro = new Livro(existingId,
                "Livro teste",
                "Autor desconhecido",
                LocalDate.of(2021,11,26),
                user);
        return livro;
    }

    public static LivroDTO getLivroDTO() {
        Livro livro = getLivro();
        return new LivroDTO(livro);
    }
}
