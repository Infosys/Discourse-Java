import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { InvitesService } from 'app/entities/invites/invites.service';
import { IInvites, Invites } from 'app/shared/model/invites.model';

describe('Service Tests', () => {
  describe('Invites Service', () => {
    let injector: TestBed;
    let service: InvitesService;
    let httpMock: HttpTestingController;
    let elemDefault: IInvites;
    let expectedResult: IInvites | IInvites[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(InvitesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Invites(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        currentDate,
        false,
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            redeemedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            invalidatedAt: currentDate.format(DATE_TIME_FORMAT),
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Invites', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            redeemedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            invalidatedAt: currentDate.format(DATE_TIME_FORMAT),
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            redeemedAt: currentDate,
            deletedAt: currentDate,
            invalidatedAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Invites()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Invites', () => {
        const returnedFromService = Object.assign(
          {
            inviteKey: 'BBBBBB',
            email: 'BBBBBB',
            invitedById: 'BBBBBB',
            userId: 'BBBBBB',
            redeemedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
            invalidatedAt: currentDate.format(DATE_TIME_FORMAT),
            moderator: true,
            customMessage: 'BBBBBB',
            emailedStatus: 1,
            maxRedemptionsAllowed: 1,
            redemptionCount: 1,
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
            emailToken: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            redeemedAt: currentDate,
            deletedAt: currentDate,
            invalidatedAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Invites', () => {
        const returnedFromService = Object.assign(
          {
            inviteKey: 'BBBBBB',
            email: 'BBBBBB',
            invitedById: 'BBBBBB',
            userId: 'BBBBBB',
            redeemedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
            invalidatedAt: currentDate.format(DATE_TIME_FORMAT),
            moderator: true,
            customMessage: 'BBBBBB',
            emailedStatus: 1,
            maxRedemptionsAllowed: 1,
            redemptionCount: 1,
            expiresAt: currentDate.format(DATE_TIME_FORMAT),
            emailToken: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            redeemedAt: currentDate,
            deletedAt: currentDate,
            invalidatedAt: currentDate,
            expiresAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Invites', () => {
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
