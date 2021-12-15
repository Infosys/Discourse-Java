import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserAvatarsService } from 'app/entities/user-avatars/user-avatars.service';
import { IUserAvatars, UserAvatars } from 'app/shared/model/user-avatars.model';

describe('Service Tests', () => {
  describe('UserAvatars Service', () => {
    let injector: TestBed;
    let service: UserAvatarsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserAvatars;
    let expectedResult: IUserAvatars | IUserAvatars[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserAvatarsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserAvatars(0, 'AAAAAAA', 0, 0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastGravatarDownloadAttempt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserAvatars', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastGravatarDownloadAttempt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastGravatarDownloadAttempt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserAvatars()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserAvatars', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            customUploadId: 1,
            gravatarUploadId: 1,
            lastGravatarDownloadAttempt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastGravatarDownloadAttempt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserAvatars', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            customUploadId: 1,
            gravatarUploadId: 1,
            lastGravatarDownloadAttempt: currentDate.format(DATE_TIME_FORMAT),
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastGravatarDownloadAttempt: currentDate,
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserAvatars', () => {
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
