import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AnnouncmentService } from 'app/entities/announcment/announcment.service';
import { IAnnouncment, Announcment } from 'app/shared/model/announcment.model';
import { AnnouncmentStatus } from 'app/shared/model/enumerations/announcment-status.model';

describe('Service Tests', () => {
  describe('Announcment Service', () => {
    let injector: TestBed;
    let service: AnnouncmentService;
    let httpMock: HttpTestingController;
    let elemDefault: IAnnouncment;
    let expectedResult: IAnnouncment | IAnnouncment[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AnnouncmentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Announcment(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, AnnouncmentStatus.ACTIVE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Announcment', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Announcment()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Announcment', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            raw: 'BBBBBB',
            deletedBy: 'BBBBBB',
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Announcment', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            raw: 'BBBBBB',
            deletedBy: 'BBBBBB',
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Announcment', () => {
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
