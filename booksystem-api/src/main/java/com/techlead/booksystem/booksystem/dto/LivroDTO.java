package com.techlead.booksystem.booksystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techlead.booksystem.booksystem.entities.Livro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LivroDTO {
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String autor;
    @JsonProperty("data_cadastro")
    private LocalDate dataCadastro;
    private String owner;

    public LivroDTO(String nome, String autor) {
        this.nome = nome;
        this.autor = autor;
    }

    public LivroDTO(Livro entity) {
        this.id = entity.getId();;
        this.nome = entity.getNome();
        this.autor = entity.getAutor();
        this.dataCadastro = entity.getDataCadastro();
        this.owner = entity.getSavedBy().getUsername();
    }
}
