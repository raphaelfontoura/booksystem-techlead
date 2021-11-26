import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-alerta-dialog',
  templateUrl: './alerta-dialog.component.html',
  styleUrls: ['./alerta-dialog.component.css']
})
export class AlertaDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: string) { }

  ngOnInit(): void {
  }

}
