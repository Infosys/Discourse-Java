import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GroupUsersService } from 'app/entities/group-users/group-users.service';
import { IGroupUsers, GroupUsers } from 'app/shared/model/group-users.model';

describe('Service Tests', () => {
  describe('GroupUsers Service', () => {
    let injector: TestBed;
    let service: GroupUsersService;
    let httpMock: HttpTestingController;
    let elemDefault: IGroupUsers;
    let expectedResult: IGroupUsers | IGroupUsers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GroupUsersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GroupUsers(0, 0, 'AAAAAAA', false, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GroupUsers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            firstUnreadPmAt: currentDate,
          },
          returnedFromService
        );

        service.create(new GroupUsers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GroupUsers', () => {
        const returnedFromService = Object.assign(
          {
            groupId: 1,
            userId: 'BBBBBB',
            owner: true,
            notificationLevel: 1,
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            firstUnreadPmAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GroupUsers', () => {
        const returnedFromService = Object.assign(
          {
            groupId: 1,
            userId: 'BBBBBB',
            owner: true,
            notificationLevel: 1,
            firstUnreadPmAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            firstUnreadPmAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GroupUsers', () => {
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
