package com.techlead.booksystem.booksystem.resources;

import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService service;

    @GetMapping
    public List<LivroDTO> getAll() {
        return service.getAll();
    }

    @PostMapping
    public LivroDTO save(@RequestBody LivroDTO dto) {
        return service.save(dto);
    }

}
