import { TestBed } from '@angular/core/testing';

import { ListGenreService } from './list-genre.service';

describe('ListGenreService', () => {
  let service: ListGenreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListGenreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
