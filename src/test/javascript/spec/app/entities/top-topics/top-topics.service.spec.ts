import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TopTopicsService } from 'app/entities/top-topics/top-topics.service';
import { ITopTopics, TopTopics } from 'app/shared/model/top-topics.model';

describe('Service Tests', () => {
  describe('TopTopics Service', () => {
    let injector: TestBed;
    let service: TopTopicsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopTopics;
    let expectedResult: ITopTopics | ITopTopics[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopTopicsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TopTopics(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopTopics', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TopTopics()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopTopics', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            yearlyPostsCount: 1,
            yearlyViewsCount: 1,
            yearlyLikesCount: 1,
            monthlyPostsCount: 1,
            monthlyViewsCount: 1,
            monthlyLikesCount: 1,
            weeklyPostsCount: 1,
            weeklyViewsCount: 1,
            weeklyLikesCount: 1,
            dailyPostsCount: 1,
            dailyViewsCount: 1,
            dailyLikesCount: 1,
            dailyScore: 1,
            weeklyScore: 1,
            monthlyScore: 1,
            yearlyScore: 1,
            allScore: 1,
            dailyOpLikesCount: 1,
            weeklyOpLikesCount: 1,
            monthlyOpLikesCount: 1,
            yearlyOpLikesCount: 1,
            quarterlyPostsCount: 1,
            quarterlyViewsCount: 1,
            quarterlyLikesCount: 1,
            quarterlyScore: 1,
            quarterlyOpLikesCount: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopTopics', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            yearlyPostsCount: 1,
            yearlyViewsCount: 1,
            yearlyLikesCount: 1,
            monthlyPostsCount: 1,
            monthlyViewsCount: 1,
            monthlyLikesCount: 1,
            weeklyPostsCount: 1,
            weeklyViewsCount: 1,
            weeklyLikesCount: 1,
            dailyPostsCount: 1,
            dailyViewsCount: 1,
            dailyLikesCount: 1,
            dailyScore: 1,
            weeklyScore: 1,
            monthlyScore: 1,
            yearlyScore: 1,
            allScore: 1,
            dailyOpLikesCount: 1,
            weeklyOpLikesCount: 1,
            monthlyOpLikesCount: 1,
            yearlyOpLikesCount: 1,
            quarterlyPostsCount: 1,
            quarterlyViewsCount: 1,
            quarterlyLikesCount: 1,
            quarterlyScore: 1,
            quarterlyOpLikesCount: 1,
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

      it('should delete a TopTopics', () => {
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
