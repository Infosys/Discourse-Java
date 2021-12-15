import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TopicEmbedsService } from 'app/entities/topic-embeds/topic-embeds.service';
import { ITopicEmbeds, TopicEmbeds } from 'app/shared/model/topic-embeds.model';

describe('Service Tests', () => {
  describe('TopicEmbeds Service', () => {
    let injector: TestBed;
    let service: TopicEmbedsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicEmbeds;
    let expectedResult: ITopicEmbeds | ITopicEmbeds[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicEmbedsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TopicEmbeds(0, 0, 0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
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

      it('should create a TopicEmbeds', () => {
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

        service.create(new TopicEmbeds()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicEmbeds', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            postId: 1,
            embedUrl: 'BBBBBB',
            contentSha1: 'BBBBBB',
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
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

      it('should return a list of TopicEmbeds', () => {
        const returnedFromService = Object.assign(
          {
            topicId: 1,
            postId: 1,
            embedUrl: 'BBBBBB',
            contentSha1: 'BBBBBB',
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
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

      it('should delete a TopicEmbeds', () => {
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
