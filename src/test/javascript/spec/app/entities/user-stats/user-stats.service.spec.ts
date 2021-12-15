import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserStatsService } from 'app/entities/user-stats/user-stats.service';
import { IUserStats, UserStats } from 'app/shared/model/user-stats.model';

describe('Service Tests', () => {
  describe('UserStats Service', () => {
    let injector: TestBed;
    let service: UserStatsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserStats;
    let expectedResult: IUserStats | IUserStats[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserStatsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserStats(
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        0,
        currentDate,
        0,
        0,
        0,
        currentDate,
        0,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            newSince: currentDate.format(DATE_TIME_FORMAT),
            readFaq: currentDate.format(DATE_TIME_FORMAT),
            firstPostCreatedAt: currentDate.format(DATE_TIME_FORMAT),
            resetBounceScoreAfter: currentDate.format(DATE_TIME_FORMAT),
            firstUnreadAt: currentDate.format(DATE_TIME_FORMAT),
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
            digestAttemptedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserStats', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            newSince: currentDate.format(DATE_TIME_FORMAT),
            readFaq: currentDate.format(DATE_TIME_FORMAT),
            firstPostCreatedAt: currentDate.format(DATE_TIME_FORMAT),
            resetBounceScoreAfter: currentDate.format(DATE_TIME_FORMAT),
            firstUnreadAt: currentDate.format(DATE_TIME_FORMAT),
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
            digestAttemptedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            newSince: currentDate,
            readFaq: currentDate,
            firstPostCreatedAt: currentDate,
            resetBounceScoreAfter: currentDate,
            firstUnreadAt: currentDate,
            firstUnreadPmAt: currentDate,
            digestAttemptedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserStats()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserStats', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicsEntered: 1,
            timeRead: 1,
            daysVisited: 1,
            postsReadCount: 1,
            likesGiven: 1,
            likesReceived: 1,
            newSince: currentDate.format(DATE_TIME_FORMAT),
            readFaq: currentDate.format(DATE_TIME_FORMAT),
            firstPostCreatedAt: currentDate.format(DATE_TIME_FORMAT),
            postCount: 1,
            topicCount: 1,
            bounceScore: 1,
            resetBounceScoreAfter: currentDate.format(DATE_TIME_FORMAT),
            flagsAgreed: 1,
            flagsDisagreed: 1,
            flagsIgnored: 1,
            firstUnreadAt: currentDate.format(DATE_TIME_FORMAT),
            distinctBadgeCount: 1,
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
            digestAttemptedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            newSince: currentDate,
            readFaq: currentDate,
            firstPostCreatedAt: currentDate,
            resetBounceScoreAfter: currentDate,
            firstUnreadAt: currentDate,
            firstUnreadPmAt: currentDate,
            digestAttemptedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserStats', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicsEntered: 1,
            timeRead: 1,
            daysVisited: 1,
            postsReadCount: 1,
            likesGiven: 1,
            likesReceived: 1,
            newSince: currentDate.format(DATE_TIME_FORMAT),
            readFaq: currentDate.format(DATE_TIME_FORMAT),
            firstPostCreatedAt: currentDate.format(DATE_TIME_FORMAT),
            postCount: 1,
            topicCount: 1,
            bounceScore: 1,
            resetBounceScoreAfter: currentDate.format(DATE_TIME_FORMAT),
            flagsAgreed: 1,
            flagsDisagreed: 1,
            flagsIgnored: 1,
            firstUnreadAt: currentDate.format(DATE_TIME_FORMAT),
            distinctBadgeCount: 1,
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
            digestAttemptedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            newSince: currentDate,
            readFaq: currentDate,
            firstPostCreatedAt: currentDate,
            resetBounceScoreAfter: currentDate,
            firstUnreadAt: currentDate,
            firstUnreadPmAt: currentDate,
            digestAttemptedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserStats', () => {
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
