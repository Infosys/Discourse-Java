import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GivenDailyLikesService } from 'app/entities/given-daily-likes/given-daily-likes.service';
import { IGivenDailyLikes, GivenDailyLikes } from 'app/shared/model/given-daily-likes.model';

describe('Service Tests', () => {
  describe('GivenDailyLikes Service', () => {
    let injector: TestBed;
    let service: GivenDailyLikesService;
    let httpMock: HttpTestingController;
    let elemDefault: IGivenDailyLikes;
    let expectedResult: IGivenDailyLikes | IGivenDailyLikes[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GivenDailyLikesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GivenDailyLikes(0, 'AAAAAAA', 0, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            givenDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GivenDailyLikes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            givenDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            givenDate: currentDate,
          },
          returnedFromService
        );

        service.create(new GivenDailyLikes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GivenDailyLikes', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            likesGiven: 1,
            givenDate: currentDate.format(DATE_FORMAT),
            limitReached: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            givenDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GivenDailyLikes', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            likesGiven: 1,
            givenDate: currentDate.format(DATE_FORMAT),
            limitReached: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            givenDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GivenDailyLikes', () => {
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
