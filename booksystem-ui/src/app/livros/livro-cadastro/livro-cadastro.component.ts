import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { LivrosService } from '../services/livros.service';

@Component({
  selector: 'app-livro-cadastro',
  templateUrl: './livro-cadastro.component.html',
  styleUrls: ['./livro-cadastro.component.css']
})
export class LivroCadastroComponent implements OnInit {

  livroForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private livroService: LivrosService, private router: Router) { }

  ngOnInit(): void {
    this.livroForm = this.formBuilder.group({
      nome : [null, Validators.required],
      autor : [null, Validators.required],
    });
  }

  onSubmit() {
    this.livroService.save(this.livroForm.value).subscribe( res => {
      console.log("LivroCadastro: " + res);
      this.router.navigate(['/livros']).then(_ => console.log('Livro cadastrado com sucesso!'));
    } )
  }

}
