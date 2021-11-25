package com.techlead.booksystem.booksystem.services;

import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.entities.Livro;
import com.techlead.booksystem.booksystem.entities.User;
import com.techlead.booksystem.booksystem.repositories.LivroRepository;
import com.techlead.booksystem.booksystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<LivroDTO> getAll() {
        var livros = repository.findAll();
        return livros.stream().map(LivroDTO::new).collect(Collectors.toList());
    }

    public LivroDTO findById(Long id) {
        return new LivroDTO(repository.getById(id));
    }

    public LivroDTO save(LivroDTO input) {
        Livro livro = new Livro();
        livro.setNome(input.getNome());
        livro.setAutor(input.getAutor());
        livro.setDataCadastro(LocalDate.now());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        livro.setSavedByUser(user);
        livro = repository.save(livro);
        return new LivroDTO(livro);
    }
}
