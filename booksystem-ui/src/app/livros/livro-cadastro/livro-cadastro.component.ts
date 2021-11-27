import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, switchMap } from 'rxjs/operators';
import { Livro } from '../models/livro';

import { LivrosService } from '../services/livros.service';

@Component({
  selector: 'app-livro-cadastro',
  templateUrl: './livro-cadastro.component.html',
  styleUrls: ['./livro-cadastro.component.css'],
})
export class LivroCadastroComponent implements OnInit {
  livroForm!: FormGroup;
  id?: number;

  constructor(
    private formBuilder: FormBuilder,
    private livroService: LivrosService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params
    .pipe(
      map(params => params.id),
      switchMap(id => {
        this.id = id;
        return this.livroService.getById(id);
      })
    ).subscribe(livro => {
        this.updateForm(livro);
    });

    this.livroForm = this.formBuilder.group({
      id: [null],
      nome: [null, Validators.required],
      autor: [null, Validators.required],
      data_cadastro: [null],
    });
  }

  updateForm(livro: Livro) {
    this.livroForm.patchValue({
      nome: livro.nome,
      autor: livro.autor,
      id: livro.id,
      data_cadastro: livro.data_cadastro,
    });
  }

  onSubmit() {
    if (this.id != null) {
      this.livroService.edit(this.livroForm.value).subscribe((response) => {
        console.log(response);
        this.router.navigate(['/livros']);
      });
    } else {
      this.livroService.save(this.livroForm.value).subscribe((res) => {
        console.log('LivroCadastro: ' + res);
        this.router
          .navigate(['/livros'])
          .then((_) => console.log('Livro cadastrado com sucesso!'));
      });
    }
  }
}
