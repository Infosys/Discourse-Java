import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ScreenedUrlsService } from 'app/entities/screened-urls/screened-urls.service';
import { IScreenedUrls, ScreenedUrls } from 'app/shared/model/screened-urls.model';

describe('Service Tests', () => {
  describe('ScreenedUrls Service', () => {
    let injector: TestBed;
    let service: ScreenedUrlsService;
    let httpMock: HttpTestingController;
    let elemDefault: IScreenedUrls;
    let expectedResult: IScreenedUrls | IScreenedUrls[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ScreenedUrlsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ScreenedUrls(0, 'AAAAAAA', 'AAAAAAA', 0, 0, currentDate, 'AAAAAAA');
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

      it('should create a ScreenedUrls', () => {
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

        service.create(new ScreenedUrls()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ScreenedUrls', () => {
        const returnedFromService = Object.assign(
          {
            url: 'BBBBBB',
            domain: 'BBBBBB',
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

      it('should return a list of ScreenedUrls', () => {
        const returnedFromService = Object.assign(
          {
            url: 'BBBBBB',
            domain: 'BBBBBB',
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

      it('should delete a ScreenedUrls', () => {
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
