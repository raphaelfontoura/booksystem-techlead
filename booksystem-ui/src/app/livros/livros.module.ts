import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LivrosComponent } from './livros/livros.component';
import { LivroCadastroComponent } from './livro-cadastro/livro-cadastro.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    LivrosComponent,
    LivroCadastroComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: []
})
export class LivrosModule { }
