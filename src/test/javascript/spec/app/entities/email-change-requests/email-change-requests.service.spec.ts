import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmailChangeRequestsService } from 'app/entities/email-change-requests/email-change-requests.service';
import { IEmailChangeRequests, EmailChangeRequests } from 'app/shared/model/email-change-requests.model';

describe('Service Tests', () => {
  describe('EmailChangeRequests Service', () => {
    let injector: TestBed;
    let service: EmailChangeRequestsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmailChangeRequests;
    let expectedResult: IEmailChangeRequests | IEmailChangeRequests[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmailChangeRequestsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EmailChangeRequests(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmailChangeRequests', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EmailChangeRequests()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmailChangeRequests', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            oldEmail: 'BBBBBB',
            newEmail: 'BBBBBB',
            oldEmailTokenId: 1,
            newEmailTokenId: 1,
            changeState: 1,
            requestedByUserId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmailChangeRequests', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            oldEmail: 'BBBBBB',
            newEmail: 'BBBBBB',
            oldEmailTokenId: 1,
            newEmailTokenId: 1,
            changeState: 1,
            requestedByUserId: 'BBBBBB',
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

      it('should delete a EmailChangeRequests', () => {
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
