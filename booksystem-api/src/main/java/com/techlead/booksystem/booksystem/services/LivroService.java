package com.techlead.booksystem.booksystem.services;

import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.entities.Livro;
import com.techlead.booksystem.booksystem.entities.User;
import com.techlead.booksystem.booksystem.repositories.LivroRepository;
import com.techlead.booksystem.booksystem.repositories.UserRepository;
import com.techlead.booksystem.booksystem.services.exceptions.ResourceNotFoundException;
import com.techlead.booksystem.booksystem.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<LivroDTO> getAll() {
        var livros = repository.findAll();
        return livros.stream().map(LivroDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LivroDTO findById(Long id) {
        Livro livro = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado."));
        return new LivroDTO(livro);
    }

    @Transactional
    public LivroDTO save(LivroDTO input) {
        Livro livro = new Livro();
        livro.setNome(input.getNome());
        livro.setAutor(input.getAutor());
        livro.setDataCadastro(input.getDataCadastro() == null ? LocalDate.now() : input.getDataCadastro());
        User user = getUserAuth();
        livro.setSavedBy(user);
        return new LivroDTO(repository.save(livro));
    }

    @Transactional
    public LivroDTO edit(Long id, LivroDTO dto) {
        Livro livro = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livro nao encontrado."));
        User user = getUserAuth();
        if (! livro.getSavedBy().equals(user) && ! user.hasHole("ADMIN")) {
            throw new UnauthorizedException("Usuário não autorizado.");
        }
        livro.setAutor(dto.getAutor());
        livro.setNome(dto.getNome());
        livro.setDataCadastro(dto.getDataCadastro());
        livro = repository.save(livro);
        return new LivroDTO(livro);
    }

    @Transactional
    public void delete(Long id) {
        Livro livro = repository.getById(id);
        User user = getUserAuth();
        if (! livro.getSavedBy().equals(user) && ! user.hasHole("ADMIN")) {
            throw new UnauthorizedException("Usuário não autorizado.");
        }
        repository.delete(livro);
    }

    private User getUserAuth() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }
}
