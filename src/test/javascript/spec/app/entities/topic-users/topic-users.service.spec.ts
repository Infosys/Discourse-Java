import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TopicUsersService } from 'app/entities/topic-users/topic-users.service';
import { ITopicUsers, TopicUsers } from 'app/shared/model/topic-users.model';

describe('Service Tests', () => {
  describe('TopicUsers Service', () => {
    let injector: TestBed;
    let service: TopicUsersService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicUsers;
    let expectedResult: ITopicUsers | ITopicUsers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicUsersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TopicUsers(
        0,
        'AAAAAAA',
        0,
        false,
        0,
        0,
        currentDate,
        currentDate,
        0,
        currentDate,
        0,
        0,
        currentDate,
        0,
        false,
        false,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            firstVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            notificationsChangedAt: currentDate.format(DATE_TIME_FORMAT),
            clearedPinnedAt: currentDate.format(DATE_TIME_FORMAT),
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            firstVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            notificationsChangedAt: currentDate.format(DATE_TIME_FORMAT),
            clearedPinnedAt: currentDate.format(DATE_TIME_FORMAT),
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastVisitedAt: currentDate,
            firstVisitedAt: currentDate,
            notificationsChangedAt: currentDate,
            clearedPinnedAt: currentDate,
            lastPostedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new TopicUsers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicUsers', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicId: 1,
            posted: true,
            lastReadPostNumber: 1,
            highestSeenPostNumber: 1,
            lastVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            firstVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            notificationLevel: 1,
            notificationsChangedAt: currentDate.format(DATE_TIME_FORMAT),
            notificationsReasonId: 1,
            totalMsecsViewed: 1,
            clearedPinnedAt: currentDate.format(DATE_TIME_FORMAT),
            lastEmailedPostNumber: 1,
            liked: true,
            bookmarked: true,
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastVisitedAt: currentDate,
            firstVisitedAt: currentDate,
            notificationsChangedAt: currentDate,
            clearedPinnedAt: currentDate,
            lastPostedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopicUsers', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicId: 1,
            posted: true,
            lastReadPostNumber: 1,
            highestSeenPostNumber: 1,
            lastVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            firstVisitedAt: currentDate.format(DATE_TIME_FORMAT),
            notificationLevel: 1,
            notificationsChangedAt: currentDate.format(DATE_TIME_FORMAT),
            notificationsReasonId: 1,
            totalMsecsViewed: 1,
            clearedPinnedAt: currentDate.format(DATE_TIME_FORMAT),
            lastEmailedPostNumber: 1,
            liked: true,
            bookmarked: true,
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastVisitedAt: currentDate,
            firstVisitedAt: currentDate,
            notificationsChangedAt: currentDate,
            clearedPinnedAt: currentDate,
            lastPostedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TopicUsers', () => {
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
