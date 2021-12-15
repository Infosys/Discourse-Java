import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SearchLogsService } from 'app/entities/search-logs/search-logs.service';
import { ISearchLogs, SearchLogs } from 'app/shared/model/search-logs.model';

describe('Service Tests', () => {
  describe('SearchLogs Service', () => {
    let injector: TestBed;
    let service: SearchLogsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISearchLogs;
    let expectedResult: ISearchLogs | ISearchLogs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SearchLogsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SearchLogs(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SearchLogs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SearchLogs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SearchLogs', () => {
        const returnedFromService = Object.assign(
          {
            term: 'BBBBBB',
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
            searchResultId: 1,
            searchType: 1,
            searchResultType: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SearchLogs', () => {
        const returnedFromService = Object.assign(
          {
            term: 'BBBBBB',
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
            searchResultId: 1,
            searchType: 1,
            searchResultType: 1,
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

      it('should delete a SearchLogs', () => {
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
