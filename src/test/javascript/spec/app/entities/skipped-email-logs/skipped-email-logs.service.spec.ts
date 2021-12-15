import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SkippedEmailLogsService } from 'app/entities/skipped-email-logs/skipped-email-logs.service';
import { ISkippedEmailLogs, SkippedEmailLogs } from 'app/shared/model/skipped-email-logs.model';

describe('Service Tests', () => {
  describe('SkippedEmailLogs Service', () => {
    let injector: TestBed;
    let service: SkippedEmailLogsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISkippedEmailLogs;
    let expectedResult: ISkippedEmailLogs | ISkippedEmailLogs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SkippedEmailLogsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SkippedEmailLogs(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SkippedEmailLogs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SkippedEmailLogs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SkippedEmailLogs', () => {
        const returnedFromService = Object.assign(
          {
            emailType: 'BBBBBB',
            toAddress: 'BBBBBB',
            userId: 'BBBBBB',
            postId: 1,
            reasonType: 1,
            customReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SkippedEmailLogs', () => {
        const returnedFromService = Object.assign(
          {
            emailType: 'BBBBBB',
            toAddress: 'BBBBBB',
            userId: 'BBBBBB',
            postId: 1,
            reasonType: 1,
            customReason: 'BBBBBB',
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

      it('should delete a SkippedEmailLogs', () => {
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
