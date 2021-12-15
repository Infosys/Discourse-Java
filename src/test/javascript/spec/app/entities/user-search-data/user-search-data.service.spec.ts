import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserSearchDataService } from 'app/entities/user-search-data/user-search-data.service';
import { IUserSearchData, UserSearchData } from 'app/shared/model/user-search-data.model';

describe('Service Tests', () => {
  describe('UserSearchData Service', () => {
    let injector: TestBed;
    let service: UserSearchDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserSearchData;
    let expectedResult: IUserSearchData | IUserSearchData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserSearchDataService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserSearchData(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserSearchData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserSearchData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserSearchData', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
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

      it('should return a list of UserSearchData', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
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

      it('should delete a UserSearchData', () => {
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
