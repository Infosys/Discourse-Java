import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TopicSearchDataService } from 'app/entities/topic-search-data/topic-search-data.service';
import { ITopicSearchData, TopicSearchData } from 'app/shared/model/topic-search-data.model';

describe('Service Tests', () => {
  describe('TopicSearchData Service', () => {
    let injector: TestBed;
    let service: TopicSearchDataService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicSearchData;
    let expectedResult: ITopicSearchData | ITopicSearchData[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicSearchDataService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TopicSearchData(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicSearchData', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TopicSearchData()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicSearchData', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            rawData: 'BBBBBB',
            locale: 'BBBBBB',
            searchData: 'BBBBBB',
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

      it('should return a list of TopicSearchData', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            rawData: 'BBBBBB',
            locale: 'BBBBBB',
            searchData: 'BBBBBB',
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

      it('should delete a TopicSearchData', () => {
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
