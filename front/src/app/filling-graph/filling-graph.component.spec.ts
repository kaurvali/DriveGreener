import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FillingGraphComponent } from './filling-graph.component';

describe('FillingGraphComponent', () => {
  let component: FillingGraphComponent;
  let fixture: ComponentFixture<FillingGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FillingGraphComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FillingGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
