import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFillingComponent } from './add-filling.component';

describe('AddFillingComponent', () => {
  let component: AddFillingComponent;
  let fixture: ComponentFixture<AddFillingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddFillingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFillingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
