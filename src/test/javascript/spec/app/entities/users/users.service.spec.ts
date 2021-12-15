import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UsersService } from 'app/entities/users/users.service';
import { IUsers, Users } from 'app/shared/model/users.model';

describe('Service Tests', () => {
  describe('Users Service', () => {
    let injector: TestBed;
    let service: UsersService;
    let httpMock: HttpTestingController;
    let elemDefault: IUsers;
    let expectedResult: IUsers | IUsers[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UsersService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Users(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        currentDate,
        false,
        currentDate,
        0,
        false,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        0,
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        currentDate,
        currentDate,
        0,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
            lastEmailedAt: currentDate.format(DATE_TIME_FORMAT),
            approvedAt: currentDate.format(DATE_TIME_FORMAT),
            previousVisitAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedTill: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT),
            firstSeenAt: currentDate.format(DATE_TIME_FORMAT),
            silencedTill: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Users', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
            lastEmailedAt: currentDate.format(DATE_TIME_FORMAT),
            approvedAt: currentDate.format(DATE_TIME_FORMAT),
            previousVisitAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedTill: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT),
            firstSeenAt: currentDate.format(DATE_TIME_FORMAT),
            silencedTill: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastPostedAt: currentDate,
            lastSeenAt: currentDate,
            lastEmailedAt: currentDate,
            approvedAt: currentDate,
            previousVisitAt: currentDate,
            suspendedAt: currentDate,
            suspendedTill: currentDate,
            dateOfBirth: currentDate,
            firstSeenAt: currentDate,
            silencedTill: currentDate,
          },
          returnedFromService
        );

        service.create(new Users()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Users', () => {
        const returnedFromService = Object.assign(
          {
            username: 'BBBBBB',
            name: 'BBBBBB',
            seenNotificationId: 1,
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            passwordHash: 'BBBBBB',
            salt: 'BBBBBB',
            active: true,
            usernameLower: 'BBBBBB',
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
            admin: true,
            lastEmailedAt: currentDate.format(DATE_TIME_FORMAT),
            trustLevel: 1,
            approved: true,
            approvedById: 'BBBBBB',
            approvedAt: currentDate.format(DATE_TIME_FORMAT),
            previousVisitAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedTill: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT),
            views: 1,
            flagLevel: 1,
            ipAddress: 'BBBBBB',
            moderator: true,
            title: 'BBBBBB',
            uploadedAvatarId: 1,
            locale: 'BBBBBB',
            primaryGroupId: 1,
            registrationIpAddress: 'BBBBBB',
            staged: true,
            firstSeenAt: currentDate.format(DATE_TIME_FORMAT),
            silencedTill: currentDate.format(DATE_TIME_FORMAT),
            groupLockedTrustLevel: 1,
            manualLockedTrustLevel: 1,
            secureIdentifier: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastPostedAt: currentDate,
            lastSeenAt: currentDate,
            lastEmailedAt: currentDate,
            approvedAt: currentDate,
            previousVisitAt: currentDate,
            suspendedAt: currentDate,
            suspendedTill: currentDate,
            dateOfBirth: currentDate,
            firstSeenAt: currentDate,
            silencedTill: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Users', () => {
        const returnedFromService = Object.assign(
          {
            username: 'BBBBBB',
            name: 'BBBBBB',
            seenNotificationId: 1,
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            passwordHash: 'BBBBBB',
            salt: 'BBBBBB',
            active: true,
            usernameLower: 'BBBBBB',
            lastSeenAt: currentDate.format(DATE_TIME_FORMAT),
            admin: true,
            lastEmailedAt: currentDate.format(DATE_TIME_FORMAT),
            trustLevel: 1,
            approved: true,
            approvedById: 'BBBBBB',
            approvedAt: currentDate.format(DATE_TIME_FORMAT),
            previousVisitAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedAt: currentDate.format(DATE_TIME_FORMAT),
            suspendedTill: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT),
            views: 1,
            flagLevel: 1,
            ipAddress: 'BBBBBB',
            moderator: true,
            title: 'BBBBBB',
            uploadedAvatarId: 1,
            locale: 'BBBBBB',
            primaryGroupId: 1,
            registrationIpAddress: 'BBBBBB',
            staged: true,
            firstSeenAt: currentDate.format(DATE_TIME_FORMAT),
            silencedTill: currentDate.format(DATE_TIME_FORMAT),
            groupLockedTrustLevel: 1,
            manualLockedTrustLevel: 1,
            secureIdentifier: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastPostedAt: currentDate,
            lastSeenAt: currentDate,
            lastEmailedAt: currentDate,
            approvedAt: currentDate,
            previousVisitAt: currentDate,
            suspendedAt: currentDate,
            suspendedTill: currentDate,
            dateOfBirth: currentDate,
            firstSeenAt: currentDate,
            silencedTill: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Users', () => {
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
