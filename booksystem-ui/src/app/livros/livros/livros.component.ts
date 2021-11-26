import { Component, IterableDiffers, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { AlertaDialogComponent } from 'src/app/shared/components/alerta-dialog/alerta-dialog.component';

import { Livro } from '../models/livro';
import { LivrosService } from '../services/livros.service';

@Component({
  selector: 'app-livros',
  templateUrl: './livros.component.html',
  styleUrls: ['./livros.component.css'],
})
export class LivrosComponent implements OnInit {
  livros$: Observable<Livro[]>;
  dataSource = [];
  displayedColumns = ['nome', 'autor', 'data_cadastro', 'acoes'];
  resultDialog = false;

  constructor(
    public livrosService: LivrosService,
    private _snackBar: MatSnackBar,
    public dialog: MatDialog,
    private router: Router
  ) {
    this.livros$ = this.livrosService.list().pipe(
      catchError((err) => {
        console.error(err);
        this.openSnackBar();
        return of([]);
      })
    );
  }

  ngOnInit(): void {
    
  }

  openSnackBar() {
    this._snackBar.open(
      'Alienígenas estão provocando Falha na Conexão. :( ',
      'Fechar',
      {
        horizontalPosition: 'center',
        verticalPosition: 'top',
      }
    );
  }

  deleteItem(item: Livro) {
    const alertRef = this.dialog.open(AlertaDialogComponent, { data: "Tem certeza que deseja excluir este item?" });
    alertRef.afterClosed().subscribe(result => {
      if (result) {
        this.livrosService.delete(item).subscribe( response => {
          console.log(response);
          this.reloadComponent();
        })
      }
    })
    
  }

  reloadComponent() {
    let currentUrl = this.router.url;
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([currentUrl]);
 }

  editItem(item: Livro) {
    console.log(item);
  }
}
