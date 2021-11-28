import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import jwt_decode from 'jwt-decode';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AlertaDialogComponent } from 'src/app/shared/components/alerta-dialog/alerta-dialog.component';

import { Livro } from '../models/livro';
import { User } from '../models/user';
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
  token: string = '';
  user: User = {user_name: '', authorities: ['']}

  constructor(
    public livrosService: LivrosService,
    private _snackBar: MatSnackBar,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute 
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
    const decodeToken = jwt_decode(localStorage.getItem('access_token') || '');
    this.user = decodeToken as User;
    console.log(this.user.user_name);
    console.log(this.user.authorities);
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
    this.router.navigate(['cadastro', item.id], {relativeTo: this.route});
  }

  canEdit(item: Livro) {
    return item.owner === this.user.user_name || this.user.authorities.includes("ROLE_ADMIN");
  }
}
