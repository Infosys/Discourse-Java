import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserApiKeysService } from 'app/entities/user-api-keys/user-api-keys.service';
import { IUserApiKeys, UserApiKeys } from 'app/shared/model/user-api-keys.model';

describe('Service Tests', () => {
  describe('UserApiKeys Service', () => {
    let injector: TestBed;
    let service: UserApiKeysService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserApiKeys;
    let expectedResult: IUserApiKeys | IUserApiKeys[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserApiKeysService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserApiKeys(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserApiKeys', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            revokedAt: currentDate,
            lastUsedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserApiKeys()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserApiKeys', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            clientId: 'BBBBBB',
            applicationName: 'BBBBBB',
            pushUrl: 'BBBBBB',
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
            scopes: 'BBBBBB',
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
            keyHash: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            revokedAt: currentDate,
            lastUsedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserApiKeys', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            clientId: 'BBBBBB',
            applicationName: 'BBBBBB',
            pushUrl: 'BBBBBB',
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
            scopes: 'BBBBBB',
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
            keyHash: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            revokedAt: currentDate,
            lastUsedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserApiKeys', () => {
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
