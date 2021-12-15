import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TopicLinksService } from 'app/entities/topic-links/topic-links.service';
import { ITopicLinks, TopicLinks } from 'app/shared/model/topic-links.model';

describe('Service Tests', () => {
  describe('TopicLinks Service', () => {
    let injector: TestBed;
    let service: TopicLinksService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicLinks;
    let expectedResult: ITopicLinks | ITopicLinks[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicLinksService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TopicLinks(
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        false,
        0,
        0,
        'AAAAAAA',
        currentDate,
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            crawledAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicLinks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            crawledAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            crawledAt: currentDate,
          },
          returnedFromService
        );

        service.create(new TopicLinks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicLinks', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            postId: 1,
            userId: 'BBBBBB',
            url: 'BBBBBB',
            domain: 'BBBBBB',
            internal: true,
            linkTopicId: 1,
            reflection: true,
            clicks: 1,
            linkPostId: 1,
            title: 'BBBBBB',
            crawledAt: currentDate.format(DATE_TIME_FORMAT),
            quote: true,
            extension: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            crawledAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopicLinks', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            postId: 1,
            userId: 'BBBBBB',
            url: 'BBBBBB',
            domain: 'BBBBBB',
            internal: true,
            linkTopicId: 1,
            reflection: true,
            clicks: 1,
            linkPostId: 1,
            title: 'BBBBBB',
            crawledAt: currentDate.format(DATE_TIME_FORMAT),
            quote: true,
            extension: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            crawledAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TopicLinks', () => {
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
