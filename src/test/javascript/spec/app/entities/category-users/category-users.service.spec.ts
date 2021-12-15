import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CategoryUsersService } from 'app/entities/category-users/category-users.service';
import { ICategoryUsers, CategoryUsers } from 'app/shared/model/category-users.model';

describe('Service Tests', () => {
  describe('CategoryUsers Service', () => {
    let injector: TestBed;
    let service: CategoryUsersService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategoryUsers;
    let expectedResult: ICategoryUsers | ICategoryUsers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CategoryUsersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CategoryUsers(0, 0, 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CategoryUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastSeenAt: currentDate,
          },
          returnedFromService
        );

        service.create(new CategoryUsers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategoryUsers', () => {
        const returnedFromService = Object.assign(
          {
            categoryId: 1,
            userId: 'BBBBBB',
            notificationLevel: 1,
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastSeenAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategoryUsers', () => {
        const returnedFromService = Object.assign(
          {
            categoryId: 1,
            userId: 'BBBBBB',
            notificationLevel: 1,
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastSeenAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CategoryUsers', () => {
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
