import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ScreenedIpAddressesService } from 'app/entities/screened-ip-addresses/screened-ip-addresses.service';
import { IScreenedIpAddresses, ScreenedIpAddresses } from 'app/shared/model/screened-ip-addresses.model';

describe('Service Tests', () => {
  describe('ScreenedIpAddresses Service', () => {
    let injector: TestBed;
    let service: ScreenedIpAddressesService;
    let httpMock: HttpTestingController;
    let elemDefault: IScreenedIpAddresses;
    let expectedResult: IScreenedIpAddresses | IScreenedIpAddresses[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ScreenedIpAddressesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ScreenedIpAddresses(0, 'AAAAAAA', 0, 0, currentDate);
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

      it('should create a ScreenedIpAddresses', () => {
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

        service.create(new ScreenedIpAddresses()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ScreenedIpAddresses', () => {
        const returnedFromService = Object.assign(
          {
            ipAddress: 'BBBBBB',
            actionType: 1,
            matchCount: 1,
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

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ScreenedIpAddresses', () => {
        const returnedFromService = Object.assign(
          {
            ipAddress: 'BBBBBB',
            actionType: 1,
            matchCount: 1,
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

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ScreenedIpAddresses', () => {
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
