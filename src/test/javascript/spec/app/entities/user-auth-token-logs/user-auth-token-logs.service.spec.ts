import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserAuthTokenLogsService } from 'app/entities/user-auth-token-logs/user-auth-token-logs.service';
import { IUserAuthTokenLogs, UserAuthTokenLogs } from 'app/shared/model/user-auth-token-logs.model';

describe('Service Tests', () => {
  describe('UserAuthTokenLogs Service', () => {
    let injector: TestBed;
    let service: UserAuthTokenLogsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserAuthTokenLogs;
    let expectedResult: IUserAuthTokenLogs | IUserAuthTokenLogs[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserAuthTokenLogsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserAuthTokenLogs(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserAuthTokenLogs', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserAuthTokenLogs()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserAuthTokenLogs', () => {
        const returnedFromService = Object.assign(
          {
            action: 'BBBBBB',
            userAuthTokenId: 1,
            userId: 'BBBBBB',
            clientIp: 'BBBBBB',
            userAgent: 'BBBBBB',
            authToken: 'BBBBBB',
            path: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserAuthTokenLogs', () => {
        const returnedFromService = Object.assign(
          {
            action: 'BBBBBB',
            userAuthTokenId: 1,
            userId: 'BBBBBB',
            clientIp: 'BBBBBB',
            userAgent: 'BBBBBB',
            authToken: 'BBBBBB',
            path: 'BBBBBB',
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

      it('should delete a UserAuthTokenLogs', () => {
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
