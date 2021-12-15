import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserBadgesService } from 'app/entities/user-badges/user-badges.service';
import { IUserBadges, UserBadges } from 'app/shared/model/user-badges.model';

describe('Service Tests', () => {
  describe('UserBadges Service', () => {
    let injector: TestBed;
    let service: UserBadgesService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserBadges;
    let expectedResult: IUserBadges | IUserBadges[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserBadgesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserBadges(0, 0, 'AAAAAAA', currentDate, 'AAAAAAA', 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            grantedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserBadges', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            grantedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            grantedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserBadges()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserBadges', () => {
        const returnedFromService = Object.assign(
          {
            badgeId: 1,
            userId: 'BBBBBB',
            grantedAt: currentDate.format(DATE_TIME_FORMAT),
            grantedById: 'BBBBBB',
            postId: 1,
            notificationId: 1,
            seq: 1,
            featuredRank: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            grantedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserBadges', () => {
        const returnedFromService = Object.assign(
          {
            badgeId: 1,
            userId: 'BBBBBB',
            grantedAt: currentDate.format(DATE_TIME_FORMAT),
            grantedById: 'BBBBBB',
            postId: 1,
            notificationId: 1,
            seq: 1,
            featuredRank: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            grantedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserBadges', () => {
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
