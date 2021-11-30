package com.techlead.booksystem.booksystem.test.services;

import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.entities.Livro;
import com.techlead.booksystem.booksystem.entities.User;
import com.techlead.booksystem.booksystem.factory.LivroFactory;
import com.techlead.booksystem.booksystem.repositories.LivroRepository;
import com.techlead.booksystem.booksystem.repositories.UserRepository;
import com.techlead.booksystem.booksystem.services.LivroService;
import com.techlead.booksystem.booksystem.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WithMockUser(username = "user", roles = {"USER"})
public class LivroServiceTests {

    @InjectMocks
    private LivroService service;
    @Mock
    private LivroRepository repository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findById_shouldReturnLivroDTO_whenIdExist() {
        Long existingId = 1L;
        Livro livro = LivroFactory.getLivro();
        when(repository.findById(existingId)).thenReturn(Optional.of(livro));

        var result = service.findById(1L);

        verify(repository, times(1)).findById(existingId);
        assertEquals(livro.getNome(), result.getNome());
    }

    @Test
    void findById_shouldReturnResourceNotFound_whenIdNotExist() {
        Long noExistingId = 99L;
        doThrow(ResourceNotFoundException.class).when(repository).findById(noExistingId);

        assertThrows(ResourceNotFoundException.class, () -> service.findById(noExistingId));
    }

    @Test
    void save_shouldReturnLivroDTO_whenValidInput() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        dto.setId(null);
        when(userRepository.findByUsername(anyString())).thenReturn(new User(1L, "user", "password"));
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO result = service.save(dto);

        verify(repository, times(1)).save(any(Livro.class));
        assertEquals(livro.getId(), result.getId());
        assertEquals(livro.getSavedBy().getUsername(), result.getOwner());
    }
}
