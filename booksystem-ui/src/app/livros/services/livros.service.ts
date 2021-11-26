import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { catchError, first, tap } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';
import { Livro } from '../models/livro';

@Injectable({
  providedIn: 'root'
})
export class LivrosService {

  private readonly BASE_URL = 'http://localhost:8080/livros'

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Livro[]>(this.BASE_URL)
      .pipe(
        first(),
        tap(livros => console.log(livros))
      );
  }
}
