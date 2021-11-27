import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { LivroCadastroComponent } from './livros/livro-cadastro/livro-cadastro.component';
import { LivrosComponent } from './livros/livros/livros.component';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: '', redirectTo: 'livros', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'livros', canActivate: [AuthGuard], component: LivrosComponent },
  { path: 'livros/cadastro', canActivate: [AuthGuard], component: LivroCadastroComponent },
  { path: 'livros/cadastro/:id', canActivate: [AuthGuard], component: LivroCadastroComponent },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
