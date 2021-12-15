import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ApiKeysService } from 'app/entities/api-keys/api-keys.service';
import { IApiKeys, ApiKeys } from 'app/shared/model/api-keys.model';

describe('Service Tests', () => {
  describe('ApiKeys Service', () => {
    let injector: TestBed;
    let service: ApiKeysService;
    let httpMock: HttpTestingController;
    let elemDefault: IApiKeys;
    let expectedResult: IApiKeys | IApiKeys[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ApiKeysService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ApiKeys(0, 'AAAAAAA', 'AAAAAAA', false, currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ApiKeys', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUsedAt: currentDate,
            revokedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new ApiKeys()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ApiKeys', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            allowedIps: 'BBBBBB',
            hidden: true,
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            keyHash: 'BBBBBB',
            truncatedKey: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUsedAt: currentDate,
            revokedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ApiKeys', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            allowedIps: 'BBBBBB',
            hidden: true,
            lastUsedAt: currentDate.format(DATE_TIME_FORMAT),
            revokedAt: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            keyHash: 'BBBBBB',
            truncatedKey: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastUsedAt: currentDate,
            revokedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ApiKeys', () => {
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
