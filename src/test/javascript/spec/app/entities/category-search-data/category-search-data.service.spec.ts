import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CategorySearchDataService } from 'app/entities/category-search-data/category-search-data.service';
import { ICategorySearchData, CategorySearchData } from 'app/shared/model/category-search-data.model';

describe('Service Tests', () => {
  describe('CategorySearchData Service', () => {
    let injector: TestBed;
    let service: CategorySearchDataService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategorySearchData;
    let expectedResult: ICategorySearchData | ICategorySearchData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CategorySearchDataService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CategorySearchData(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CategorySearchData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CategorySearchData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategorySearchData', () => {
        const returnedFromService = Object.assign(
          {
            categoryId: 1,
            searchData: 'BBBBBB',
            rawData: 'BBBBBB',
            locale: 'BBBBBB',
            version: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategorySearchData', () => {
        const returnedFromService = Object.assign(
          {
            categoryId: 1,
            searchData: 'BBBBBB',
            rawData: 'BBBBBB',
            locale: 'BBBBBB',
            version: 1,
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

      it('should delete a CategorySearchData', () => {
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
