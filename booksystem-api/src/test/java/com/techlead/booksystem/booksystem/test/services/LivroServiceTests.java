package com.techlead.booksystem.booksystem.test.services;

import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.entities.Livro;
import com.techlead.booksystem.booksystem.entities.Role;
import com.techlead.booksystem.booksystem.entities.User;
import com.techlead.booksystem.booksystem.factory.LivroFactory;
import com.techlead.booksystem.booksystem.factory.UserFactory;
import com.techlead.booksystem.booksystem.repositories.LivroRepository;
import com.techlead.booksystem.booksystem.repositories.UserRepository;
import com.techlead.booksystem.booksystem.services.LivroService;
import com.techlead.booksystem.booksystem.services.exceptions.ResourceNotFoundException;
import com.techlead.booksystem.booksystem.services.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
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
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void save_shouldReturnLivroDTO_whenValidInput() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        dto.setId(null);
        when(userRepository.findByUsername(anyString())).thenReturn(UserFactory.getUser());
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO result = service.save(dto);

        verify(repository, times(1)).save(any(Livro.class));
        assertEquals(livro.getId(), result.getId());
        assertEquals(livro.getSavedBy().getUsername(), result.getOwner());
    }

    // ==== edit ====
    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void edit_shouldReturnLivroDTO_whenValidInputAndValidUser() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        dto.setNome("Outro teste");
        when(userRepository.findByUsername(anyString())).thenReturn(UserFactory.getUser());
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO result = service.edit(dto.getId(),dto);

        verify(repository, times(1)).save(any(Livro.class));
        assertEquals(livro.getId(), result.getId());
        assertEquals(livro.getSavedBy().getUsername(), result.getOwner());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void edit_shouldReturnUnauthorizedException_whenUserNotAllowed() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        dto.setNome("Outro teste");
        when(userRepository.findByUsername(anyString())).thenReturn(new User(3L, "other", "password"));
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        when(repository.save(any(Livro.class))).thenReturn(livro);

        assertThrows(UnauthorizedException.class, () -> service.edit(dto.getId(), dto));
        verify(repository, times(0)).save(any(Livro.class));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void edit_shouldReturnLivroDTO_whenUserAdmin() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        User userAdmin = UserFactory.getAdmin();
        dto.setNome("Outro teste");
        when(userRepository.findByUsername("user")).thenReturn(UserFactory.getUser());
        when(userRepository.findByUsername("admin")).thenReturn(userAdmin);
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        livro.setNome(dto.getNome());
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO result = service.edit(dto.getId(), dto);

        verify(repository, times(1)).save(any(Livro.class));
        assertEquals(livro.getId(), result.getId());
        assertEquals(dto.getNome(), result.getNome());
    }

    // ==== delete ====
    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void delete_shouldDelete_whenValidUser() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        dto.setNome("Outro teste");
        when(userRepository.findByUsername(anyString())).thenReturn(UserFactory.getUser());
        when(repository.findById(dto.getId())).thenReturn(Optional.of(livro));
        doNothing().when(repository).deleteById(dto.getId());

        assertDoesNotThrow(() -> service.delete(dto.getId()));

        verify(repository, times(1)).findById(dto.getId());
        verify(repository, times(1)).deleteById(dto.getId());

    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void delete_shouldReturnUnauthorizedException_whenUserNotAllowed() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        dto.setNome("Outro teste");
        when(userRepository.findByUsername(anyString())).thenReturn(new User(3L, "other", "password"));
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        doNothing().when(repository).deleteById(dto.getId());

        assertThrows(UnauthorizedException.class, () -> service.delete(dto.getId()));
        verify(repository, times(0)).deleteById(dto.getId());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    void delete_shouldDelete_whenUserAdmin() {
        Livro livro = LivroFactory.getLivro();
        LivroDTO dto = LivroFactory.getLivroDTO();
        User userAdmin = UserFactory.getAdmin();
        dto.setNome("Outro teste");
        when(userRepository.findByUsername("user")).thenReturn(UserFactory.getUser());
        when(userRepository.findByUsername("admin")).thenReturn(userAdmin);
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        doNothing().when(repository).deleteById(dto.getId());

        assertDoesNotThrow(() -> service.delete(dto.getId()));

        verify(repository, times(1)).deleteById(dto.getId());
    }
}
