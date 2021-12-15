import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SchedulerStatsService } from 'app/entities/scheduler-stats/scheduler-stats.service';
import { ISchedulerStats, SchedulerStats } from 'app/shared/model/scheduler-stats.model';

describe('Service Tests', () => {
  describe('SchedulerStats Service', () => {
    let injector: TestBed;
    let service: SchedulerStatsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchedulerStats;
    let expectedResult: ISchedulerStats | ISchedulerStats[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SchedulerStatsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SchedulerStats(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, currentDate, false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SchedulerStats', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new SchedulerStats()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SchedulerStats', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            hostname: 'BBBBBB',
            pid: 1,
            durationMs: 1,
            liveSlotsStart: 1,
            liveSlotsFinish: 1,
            startedAt: currentDate.format(DATE_TIME_FORMAT),
            success: true,
            error: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SchedulerStats', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            hostname: 'BBBBBB',
            pid: 1,
            durationMs: 1,
            liveSlotsStart: 1,
            liveSlotsFinish: 1,
            startedAt: currentDate.format(DATE_TIME_FORMAT),
            success: true,
            error: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SchedulerStats', () => {
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
