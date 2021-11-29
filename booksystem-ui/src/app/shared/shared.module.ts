import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

import { AlertaDialogComponent } from './components/alerta-dialog/alerta-dialog.component';



@NgModule({
  declarations: [
    AlertaDialogComponent,
  ],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
  ],
  exports: [
    AlertaDialogComponent,
  ]
})
export class SharedModule { }
