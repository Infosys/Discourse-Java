import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StylesheetCacheService } from 'app/entities/stylesheet-cache/stylesheet-cache.service';
import { IStylesheetCache, StylesheetCache } from 'app/shared/model/stylesheet-cache.model';

describe('Service Tests', () => {
  describe('StylesheetCache Service', () => {
    let injector: TestBed;
    let service: StylesheetCacheService;
    let httpMock: HttpTestingController;
    let elemDefault: IStylesheetCache;
    let expectedResult: IStylesheetCache | IStylesheetCache[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StylesheetCacheService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new StylesheetCache(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StylesheetCache', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StylesheetCache()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StylesheetCache', () => {
        const returnedFromService = Object.assign(
          {
            target: 'BBBBBB',
            digest: 'BBBBBB',
            content: 'BBBBBB',
            themeId: 1,
            sourceMap: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StylesheetCache', () => {
        const returnedFromService = Object.assign(
          {
            target: 'BBBBBB',
            digest: 'BBBBBB',
            content: 'BBBBBB',
            themeId: 1,
            sourceMap: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StylesheetCache', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
