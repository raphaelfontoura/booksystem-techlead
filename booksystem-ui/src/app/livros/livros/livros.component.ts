import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { Livro } from '../models/livro';
import { LivrosService } from '../services/livros.service';

@Component({
  selector: 'app-livros',
  templateUrl: './livros.component.html',
  styleUrls: ['./livros.component.css']
})
export class LivrosComponent implements OnInit {

  livros$: Observable<Livro[]>;
  dataSource = [];
  displayedColumns = ['nome', 'autor', 'data_cadastro'];

  constructor(public livrosService: LivrosService) {
    this.livros$ = this.livrosService.list();
   }

  ngOnInit(): void {
    
  }

}
