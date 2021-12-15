import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserProfileViewsService } from 'app/entities/user-profile-views/user-profile-views.service';
import { IUserProfileViews, UserProfileViews } from 'app/shared/model/user-profile-views.model';

describe('Service Tests', () => {
  describe('UserProfileViews Service', () => {
    let injector: TestBed;
    let service: UserProfileViewsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserProfileViews;
    let expectedResult: IUserProfileViews | IUserProfileViews[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserProfileViewsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserProfileViews(0, 0, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            viewedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserProfileViews', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            viewedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            viewedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserProfileViews()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserProfileViews', () => {
        const returnedFromService = Object.assign(
          {
            userProfileId: 1,
            viewedAt: currentDate.format(DATE_TIME_FORMAT),
            ipAddress: 'BBBBBB',
            userId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            viewedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserProfileViews', () => {
        const returnedFromService = Object.assign(
          {
            userProfileId: 1,
            viewedAt: currentDate.format(DATE_TIME_FORMAT),
            ipAddress: 'BBBBBB',
            userId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            viewedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserProfileViews', () => {
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
