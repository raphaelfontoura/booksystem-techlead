import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

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
  displayedColumns = ['nome', 'autor', 'data_cadastro', 'acoes'];

  constructor(public livrosService: LivrosService, private _snackBar: MatSnackBar) {
    this.livros$ = this.livrosService.list().pipe(
      catchError(err => {
        console.error(err);
        this.openSnackBar();
        return of([]);
      })
    );
   }

  ngOnInit(): void {
    
  }

  openSnackBar() {
    this._snackBar.open('Alienígenas estão provocando Falha na Conexão. :( ', 'Fechar', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  deleteItem(item: Livro) {
    console.log(item);
  }

  editItem(item: Livro) {
    console.log(item)
  }

}
