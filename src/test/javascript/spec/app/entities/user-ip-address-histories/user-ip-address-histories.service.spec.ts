import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserIpAddressHistoriesService } from 'app/entities/user-ip-address-histories/user-ip-address-histories.service';
import { IUserIpAddressHistories, UserIpAddressHistories } from 'app/shared/model/user-ip-address-histories.model';

describe('Service Tests', () => {
  describe('UserIpAddressHistories Service', () => {
    let injector: TestBed;
    let service: UserIpAddressHistoriesService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserIpAddressHistories;
    let expectedResult: IUserIpAddressHistories | IUserIpAddressHistories[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserIpAddressHistoriesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserIpAddressHistories(0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserIpAddressHistories', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserIpAddressHistories()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserIpAddressHistories', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserIpAddressHistories', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
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

      it('should delete a UserIpAddressHistories', () => {
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
