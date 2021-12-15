import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserSecurityKeysService } from 'app/entities/user-security-keys/user-security-keys.service';
import { IUserSecurityKeys, UserSecurityKeys } from 'app/shared/model/user-security-keys.model';

describe('Service Tests', () => {
  describe('UserSecurityKeys Service', () => {
    let injector: TestBed;
    let service: UserSecurityKeysService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserSecurityKeys;
    let expectedResult: IUserSecurityKeys | IUserSecurityKeys[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserSecurityKeysService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserSecurityKeys(0, 0, 'AAAAAAA', 'AAAAAAA', 0, false, 'AAAAAAA', currentDate);
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

      it('should create a UserSecurityKeys', () => {
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

        service.create(new UserSecurityKeys()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserSecurityKeys', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            credentialId: 'BBBBBB',
            publicKey: 'BBBBBB',
            factorType: 1,
            enabled: true,
            name: 'BBBBBB',
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

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserSecurityKeys', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            credentialId: 'BBBBBB',
            publicKey: 'BBBBBB',
            factorType: 1,
            enabled: true,
            name: 'BBBBBB',
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

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserSecurityKeys', () => {
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
