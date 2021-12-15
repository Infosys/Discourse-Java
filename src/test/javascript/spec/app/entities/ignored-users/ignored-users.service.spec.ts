import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IgnoredUsersService } from 'app/entities/ignored-users/ignored-users.service';
import { IIgnoredUsers, IgnoredUsers } from 'app/shared/model/ignored-users.model';

describe('Service Tests', () => {
  describe('IgnoredUsers Service', () => {
    let injector: TestBed;
    let service: IgnoredUsersService;
    let httpMock: HttpTestingController;
    let elemDefault: IIgnoredUsers;
    let expectedResult: IIgnoredUsers | IIgnoredUsers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IgnoredUsersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IgnoredUsers(0, 'AAAAAAA', 'AAAAAAA', currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            summarizedAt: currentDate.format(DATE_TIME_FORMAT),
            expiringAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IgnoredUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            summarizedAt: currentDate.format(DATE_TIME_FORMAT),
            expiringAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            summarizedAt: currentDate,
            expiringAt: currentDate,
          },
          returnedFromService
        );

        service.create(new IgnoredUsers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IgnoredUsers', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            ignoredUserId: 'BBBBBB',
            summarizedAt: currentDate.format(DATE_TIME_FORMAT),
            expiringAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            summarizedAt: currentDate,
            expiringAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IgnoredUsers', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            ignoredUserId: 'BBBBBB',
            summarizedAt: currentDate.format(DATE_TIME_FORMAT),
            expiringAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            summarizedAt: currentDate,
            expiringAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IgnoredUsers', () => {
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
