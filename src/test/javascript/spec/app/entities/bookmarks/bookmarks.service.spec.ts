import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BookmarksService } from 'app/entities/bookmarks/bookmarks.service';
import { IBookmarks, Bookmarks } from 'app/shared/model/bookmarks.model';

describe('Service Tests', () => {
  describe('Bookmarks Service', () => {
    let injector: TestBed;
    let service: BookmarksService;
    let httpMock: HttpTestingController;
    let elemDefault: IBookmarks;
    let expectedResult: IBookmarks | IBookmarks[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BookmarksService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Bookmarks(0, 'AAAAAAA', 0, 0, 'AAAAAAA', 0, currentDate, currentDate, currentDate, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            reminderAt: currentDate.format(DATE_TIME_FORMAT),
            reminderLastSentAt: currentDate.format(DATE_TIME_FORMAT),
            reminderSetAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Bookmarks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            reminderAt: currentDate.format(DATE_TIME_FORMAT),
            reminderLastSentAt: currentDate.format(DATE_TIME_FORMAT),
            reminderSetAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reminderAt: currentDate,
            reminderLastSentAt: currentDate,
            reminderSetAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Bookmarks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Bookmarks', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicId: 1,
            postId: 1,
            name: 'BBBBBB',
            reminderType: 1,
            reminderAt: currentDate.format(DATE_TIME_FORMAT),
            reminderLastSentAt: currentDate.format(DATE_TIME_FORMAT),
            reminderSetAt: currentDate.format(DATE_TIME_FORMAT),
            autoDeletePreference: 1,
            pinned: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reminderAt: currentDate,
            reminderLastSentAt: currentDate,
            reminderSetAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Bookmarks', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicId: 1,
            postId: 1,
            name: 'BBBBBB',
            reminderType: 1,
            reminderAt: currentDate.format(DATE_TIME_FORMAT),
            reminderLastSentAt: currentDate.format(DATE_TIME_FORMAT),
            reminderSetAt: currentDate.format(DATE_TIME_FORMAT),
            autoDeletePreference: 1,
            pinned: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            reminderAt: currentDate,
            reminderLastSentAt: currentDate,
            reminderSetAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bookmarks', () => {
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
