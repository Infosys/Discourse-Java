import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ScreenedEmailsService } from 'app/entities/screened-emails/screened-emails.service';
import { IScreenedEmails, ScreenedEmails } from 'app/shared/model/screened-emails.model';

describe('Service Tests', () => {
  describe('ScreenedEmails Service', () => {
    let injector: TestBed;
    let service: ScreenedEmailsService;
    let httpMock: HttpTestingController;
    let elemDefault: IScreenedEmails;
    let expectedResult: IScreenedEmails | IScreenedEmails[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ScreenedEmailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ScreenedEmails(0, 'AAAAAAA', 0, 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastMatchAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ScreenedEmails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastMatchAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastMatchAt: currentDate,
          },
          returnedFromService
        );

        service.create(new ScreenedEmails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ScreenedEmails', () => {
        const returnedFromService = Object.assign(
          {
            email: 'BBBBBB',
            actionType: 1,
            matchCount: 1,
            lastMatchAt: currentDate.format(DATE_TIME_FORMAT),
            ipAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastMatchAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ScreenedEmails', () => {
        const returnedFromService = Object.assign(
          {
            email: 'BBBBBB',
            actionType: 1,
            matchCount: 1,
            lastMatchAt: currentDate.format(DATE_TIME_FORMAT),
            ipAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastMatchAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ScreenedEmails', () => {
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
