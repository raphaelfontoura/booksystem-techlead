import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';

import { AlertaDialogComponent } from './components/alerta-dialog/alerta-dialog.component';


@NgModule({
  declarations: [
    AlertaDialogComponent,
  ],
  imports: [
    CommonModule,
    MatDialogModule,
  ],
  exports: [
    AlertaDialogComponent,
  ]
})
export class SharedModule { }
