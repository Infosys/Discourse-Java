import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ReviewableScoresService } from 'app/entities/reviewable-scores/reviewable-scores.service';
import { IReviewableScores, ReviewableScores } from 'app/shared/model/reviewable-scores.model';

describe('Service Tests', () => {
  describe('ReviewableScores Service', () => {
    let injector: TestBed;
    let service: ReviewableScoresService;
    let httpMock: HttpTestingController;
    let elemDefault: IReviewableScores;
    let expectedResult: IReviewableScores | IReviewableScores[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReviewableScoresService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ReviewableScores(0, 0, 'AAAAAAA', 0, 0, 0, 0, 'AAAAAAA', currentDate, 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            reviewedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ReviewableScores', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            reviewedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new ReviewableScores()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ReviewableScores', () => {
        const returnedFromService = Object.assign(
          {
            reviewableId: 1,
            userId: 'BBBBBB',
            reviewableScoreType: 1,
            status: 1,
            score: 1,
            takeActionBonus: 1,
            reviewedById: 'BBBBBB',
            reviewedAt: currentDate.format(DATE_TIME_FORMAT),
            metaTopicId: 1,
            reason: 'BBBBBB',
            userAccuracyBonus: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ReviewableScores', () => {
        const returnedFromService = Object.assign(
          {
            reviewableId: 1,
            userId: 'BBBBBB',
            reviewableScoreType: 1,
            status: 1,
            score: 1,
            takeActionBonus: 1,
            reviewedById: 'BBBBBB',
            reviewedAt: currentDate.format(DATE_TIME_FORMAT),
            metaTopicId: 1,
            reason: 'BBBBBB',
            userAccuracyBonus: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reviewedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ReviewableScores', () => {
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
