import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertaDialogComponent } from './alerta-dialog.component';

describe('AlertaDialogComponent', () => {
  let component: AlertaDialogComponent;
  let fixture: ComponentFixture<AlertaDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlertaDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
