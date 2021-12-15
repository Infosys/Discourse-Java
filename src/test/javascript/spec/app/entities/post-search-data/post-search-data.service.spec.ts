import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PostSearchDataService } from 'app/entities/post-search-data/post-search-data.service';
import { IPostSearchData, PostSearchData } from 'app/shared/model/post-search-data.model';

describe('Service Tests', () => {
  describe('PostSearchData Service', () => {
    let injector: TestBed;
    let service: PostSearchDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IPostSearchData;
    let expectedResult: IPostSearchData | IPostSearchData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PostSearchDataService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PostSearchData(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PostSearchData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PostSearchData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PostSearchData', () => {
        const returnedFromService = Object.assign(
          {
            postId: 1,
            searchData: 'BBBBBB',
            rawData: 'BBBBBB',
            locale: 'BBBBBB',
            version: 1,
            privateMessage: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PostSearchData', () => {
        const returnedFromService = Object.assign(
          {
            postId: 1,
            searchData: 'BBBBBB',
            rawData: 'BBBBBB',
            locale: 'BBBBBB',
            version: 1,
            privateMessage: true,
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

      it('should delete a PostSearchData', () => {
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
