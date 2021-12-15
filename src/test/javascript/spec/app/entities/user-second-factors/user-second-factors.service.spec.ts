import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserSecondFactorsService } from 'app/entities/user-second-factors/user-second-factors.service';
import { IUserSecondFactors, UserSecondFactors } from 'app/shared/model/user-second-factors.model';

describe('Service Tests', () => {
  describe('UserSecondFactors Service', () => {
    let injector: TestBed;
    let service: UserSecondFactorsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserSecondFactors;
    let expectedResult: IUserSecondFactors | IUserSecondFactors[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserSecondFactorsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserSecondFactors(0, 'AAAAAAA', 0, 'AAAAAAA', false, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastUsed: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserSecondFactors', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastUsed: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUsed: currentDate,
          },
          returnedFromService
        );

        service.create(new UserSecondFactors()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserSecondFactors', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            method: 1,
            data: 'BBBBBB',
            enabled: true,
            lastUsed: currentDate.format(DATE_TIME_FORMAT),
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUsed: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserSecondFactors', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            method: 1,
            data: 'BBBBBB',
            enabled: true,
            lastUsed: currentDate.format(DATE_TIME_FORMAT),
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUsed: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserSecondFactors', () => {
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
