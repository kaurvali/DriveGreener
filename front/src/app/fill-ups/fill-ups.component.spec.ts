import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FillUpsComponent } from './fill-ups.component';

describe('FillUpsComponent', () => {
  let component: FillUpsComponent;
  let fixture: ComponentFixture<FillUpsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FillUpsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FillUpsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
