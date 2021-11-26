import { Injectable } from '@angular/core';
import { Livro } from '../models/livro';

@Injectable({
  providedIn: 'root'
})
export class LivrosService {

  constructor() { }

  list(): Livro[] {
    return [
      { autor: "Raphael", nome: "Aprendendo Angular", data_cadastro: "2020-11-10" }
    ]
  }
}
