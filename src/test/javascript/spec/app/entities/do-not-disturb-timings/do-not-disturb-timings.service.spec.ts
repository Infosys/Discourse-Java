import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DoNotDisturbTimingsService } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings.service';
import { IDoNotDisturbTimings, DoNotDisturbTimings } from 'app/shared/model/do-not-disturb-timings.model';

describe('Service Tests', () => {
  describe('DoNotDisturbTimings Service', () => {
    let injector: TestBed;
    let service: DoNotDisturbTimingsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDoNotDisturbTimings;
    let expectedResult: IDoNotDisturbTimings | IDoNotDisturbTimings[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DoNotDisturbTimingsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DoNotDisturbTimings(0, 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startsAt: currentDate.format(DATE_TIME_FORMAT),
            endsAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DoNotDisturbTimings', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startsAt: currentDate.format(DATE_TIME_FORMAT),
            endsAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startsAt: currentDate,
            endsAt: currentDate,
          },
          returnedFromService
        );

        service.create(new DoNotDisturbTimings()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DoNotDisturbTimings', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            startsAt: currentDate.format(DATE_TIME_FORMAT),
            endsAt: currentDate.format(DATE_TIME_FORMAT),
            scheduled: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startsAt: currentDate,
            endsAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DoNotDisturbTimings', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            startsAt: currentDate.format(DATE_TIME_FORMAT),
            endsAt: currentDate.format(DATE_TIME_FORMAT),
            scheduled: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startsAt: currentDate,
            endsAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DoNotDisturbTimings', () => {
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
