import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReviewablesService } from 'app/entities/reviewables/reviewables.service';
import { IReviewables, Reviewables } from 'app/shared/model/reviewables.model';

describe('Service Tests', () => {
  describe('Reviewables Service', () => {
    let injector: TestBed;
    let service: ReviewablesService;
    let httpMock: HttpTestingController;
    let elemDefault: IReviewables;
    let expectedResult: IReviewables | IReviewables[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReviewablesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Reviewables(
        0,
        'AAAAAAA',
        0,
        false,
        0,
        0,
        0,
        0,
        false,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            latestScore: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Reviewables', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            latestScore: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            latestScore: currentDate,
          },
          returnedFromService
        );

        service.create(new Reviewables()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Reviewables', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            status: 1,
            reviewableByModerator: true,
            reviewableByGroupId: 1,
            categoryId: 1,
            topicId: 1,
            score: 1,
            potentialSpam: true,
            targetId: 1,
            targetType: 'BBBBBB',
            targetCreatedById: 'BBBBBB',
            payload: 'BBBBBB',
            version: 1,
            latestScore: currentDate.format(DATE_TIME_FORMAT),
            forceReview: true,
            rejectReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            latestScore: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Reviewables', () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            status: 1,
            reviewableByModerator: true,
            reviewableByGroupId: 1,
            categoryId: 1,
            topicId: 1,
            score: 1,
            potentialSpam: true,
            targetId: 1,
            targetType: 'BBBBBB',
            targetCreatedById: 'BBBBBB',
            payload: 'BBBBBB',
            version: 1,
            latestScore: currentDate.format(DATE_TIME_FORMAT),
            forceReview: true,
            rejectReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            latestScore: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Reviewables', () => {
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
