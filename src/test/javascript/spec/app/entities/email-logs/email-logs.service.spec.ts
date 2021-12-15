import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmailLogsService } from 'app/entities/email-logs/email-logs.service';
import { IEmailLogs, EmailLogs } from 'app/shared/model/email-logs.model';

describe('Service Tests', () => {
  describe('EmailLogs Service', () => {
    let injector: TestBed;
    let service: EmailLogsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmailLogs;
    let expectedResult: IEmailLogs | IEmailLogs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmailLogsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EmailLogs(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', false, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmailLogs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EmailLogs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmailLogs', () => {
        const returnedFromService = Object.assign(
          {
            toAddress: 'BBBBBB',
            emailType: 'BBBBBB',
            userId: 'BBBBBB',
            postId: 1,
            bounceKey: 'BBBBBB',
            bounced: true,
            messageId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmailLogs', () => {
        const returnedFromService = Object.assign(
          {
            toAddress: 'BBBBBB',
            emailType: 'BBBBBB',
            userId: 'BBBBBB',
            postId: 1,
            bounceKey: 'BBBBBB',
            bounced: true,
            messageId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EmailLogs', () => {
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
