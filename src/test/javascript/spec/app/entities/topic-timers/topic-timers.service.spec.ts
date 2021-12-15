import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TopicTimersService } from 'app/entities/topic-timers/topic-timers.service';
import { ITopicTimers, TopicTimers } from 'app/shared/model/topic-timers.model';

describe('Service Tests', () => {
  describe('TopicTimers Service', () => {
    let injector: TestBed;
    let service: TopicTimersService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicTimers;
    let expectedResult: ITopicTimers | ITopicTimers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicTimersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TopicTimers(0, currentDate, 0, 'AAAAAAA', 0, false, currentDate, 'AAAAAAA', 0, false, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            executeAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicTimers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            executeAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executeAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new TopicTimers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicTimers', () => {
        const returnedFromService = Object.assign(
          {
            executeAt: currentDate.format(DATE_TIME_FORMAT),
            statusType: 1,
            userId: 'BBBBBB',
            topicId: 1,
            basedOnLastPost: true,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
            categoryId: 1,
            publicType: true,
            duration: 1,
            durationMinutes: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executeAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopicTimers', () => {
        const returnedFromService = Object.assign(
          {
            executeAt: currentDate.format(DATE_TIME_FORMAT),
            statusType: 1,
            userId: 'BBBBBB',
            topicId: 1,
            basedOnLastPost: true,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
            categoryId: 1,
            publicType: true,
            duration: 1,
            durationMinutes: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            executeAt: currentDate,
            deletedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TopicTimers', () => {
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
