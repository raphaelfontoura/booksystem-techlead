import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first, tap } from 'rxjs/operators';

import { Livro } from '../models/livro';

@Injectable({
  providedIn: 'root'
})
export class LivrosService {

  private readonly BASE_URL = 'http://localhost:8080/livros/'

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Livro[]>(this.BASE_URL)
      .pipe(
        first(),
        tap(livros => console.log(livros))
      );
  }

  getById(id: number) {
    return this.httpClient.get<Livro>(this.BASE_URL + id)
      .pipe(
        first(),
        tap(livro => console.log(livro))
      );
  }

  save(data: Livro) {
    return this.httpClient.post<Livro>(this.BASE_URL, data)
      .pipe(
        tap(result => console.log(result))
      )
  }

  edit(data: Livro) {
    return this.httpClient.put<Livro>(this.BASE_URL + data.id, data)
    .pipe(
      tap(result => console.log(result))
    )
  }

  delete(data: Livro) {
    return this.httpClient.delete<Livro>(this.BASE_URL + data.id)
    .pipe(
      tap(result => console.log(result))
    )
  }
}
