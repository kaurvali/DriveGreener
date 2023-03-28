import { TestBed } from '@angular/core/testing';

import { FillupService } from './fillup.service';

describe('FillupService', () => {
  let service: FillupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FillupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
