import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserAssociatedAccountsService } from 'app/entities/user-associated-accounts/user-associated-accounts.service';
import { IUserAssociatedAccounts, UserAssociatedAccounts } from 'app/shared/model/user-associated-accounts.model';

describe('Service Tests', () => {
  describe('UserAssociatedAccounts Service', () => {
    let injector: TestBed;
    let service: UserAssociatedAccountsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserAssociatedAccounts;
    let expectedResult: IUserAssociatedAccounts | IUserAssociatedAccounts[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserAssociatedAccountsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserAssociatedAccounts(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

      it('should create a UserAssociatedAccounts', () => {
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

        service.create(new UserAssociatedAccounts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserAssociatedAccounts', () => {
        const returnedFromService = Object.assign(
          {
            providerName: 'BBBBBB',
            providerUid: 'BBBBBB',
            userId: 'BBBBBB',
            lastUsed: currentDate.format(DATE_TIME_FORMAT),
            info: 'BBBBBB',
            credentials: 'BBBBBB',
            extra: 'BBBBBB',
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

      it('should return a list of UserAssociatedAccounts', () => {
        const returnedFromService = Object.assign(
          {
            providerName: 'BBBBBB',
            providerUid: 'BBBBBB',
            userId: 'BBBBBB',
            lastUsed: currentDate.format(DATE_TIME_FORMAT),
            info: 'BBBBBB',
            credentials: 'BBBBBB',
            extra: 'BBBBBB',
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

      it('should delete a UserAssociatedAccounts', () => {
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
