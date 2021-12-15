import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { TopicViewsService } from 'app/entities/topic-views/topic-views.service';
import { ITopicViews, TopicViews } from 'app/shared/model/topic-views.model';

describe('Service Tests', () => {
  describe('TopicViews Service', () => {
    let injector: TestBed;
    let service: TopicViewsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicViews;
    let expectedResult: ITopicViews | ITopicViews[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicViewsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TopicViews(0, 0, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            viewedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicViews', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            viewedAt: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            viewedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new TopicViews()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicViews', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            viewedAt: currentDate.format(DATE_FORMAT),
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            viewedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopicViews', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            viewedAt: currentDate.format(DATE_FORMAT),
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            viewedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TopicViews', () => {
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
