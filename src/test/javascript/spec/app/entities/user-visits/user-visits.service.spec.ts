import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UserVisitsService } from 'app/entities/user-visits/user-visits.service';
import { IUserVisits, UserVisits } from 'app/shared/model/user-visits.model';

describe('Service Tests', () => {
  describe('UserVisits Service', () => {
    let injector: TestBed;
    let service: UserVisitsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserVisits;
    let expectedResult: IUserVisits | IUserVisits[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserVisitsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserVisits(0, 'AAAAAAA', currentDate, 0, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            visitedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserVisits', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            visitedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            visitedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserVisits()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserVisits', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            visitedAt: currentDate.format(DATE_FORMAT),
            postsRead: 1,
            mobile: true,
            timeRead: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            visitedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserVisits', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            visitedAt: currentDate.format(DATE_FORMAT),
            postsRead: 1,
            mobile: true,
            timeRead: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            visitedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserVisits', () => {
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
