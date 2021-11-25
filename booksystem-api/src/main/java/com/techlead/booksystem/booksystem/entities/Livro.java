package com.techlead.booksystem.booksystem.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Livro {
    @Id
    private Long id;
    private String nome;
    private String autor;
    private LocalDate dataCadastro;
}
