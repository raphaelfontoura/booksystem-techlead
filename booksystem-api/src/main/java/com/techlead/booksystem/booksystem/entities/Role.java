package com.techlead.booksystem.booksystem.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
}
