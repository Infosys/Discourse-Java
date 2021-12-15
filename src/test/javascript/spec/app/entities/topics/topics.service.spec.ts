import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TopicsService } from 'app/entities/topics/topics.service';
import { ITopics, Topics } from 'app/shared/model/topics.model';

describe('Service Tests', () => {
  describe('Topics Service', () => {
    let injector: TestBed;
    let service: TopicsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopics;
    let expectedResult: ITopics | ITopics[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Topics(
        0,
        'AAAAAAA',
        currentDate,
        0,
        0,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        0,
        0,
        0,
        false,
        0,
        false,
        false,
        currentDate,
        false,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        currentDate,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        false,
        currentDate,
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            bumpedAt: currentDate.format(DATE_TIME_FORMAT),
            pinnedAt: currentDate.format(DATE_TIME_FORMAT),
            pinnedUntil: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Topics', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            bumpedAt: currentDate.format(DATE_TIME_FORMAT),
            pinnedAt: currentDate.format(DATE_TIME_FORMAT),
            pinnedUntil: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastPostedAt: currentDate,
            deletedAt: currentDate,
            bumpedAt: currentDate,
            pinnedAt: currentDate,
            pinnedUntil: currentDate,
          },
          returnedFromService
        );

        service.create(new Topics()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Topics', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            views: 1,
            postsCount: 1,
            userId: 'BBBBBB',
            lastPostUserId: 1,
            replyCount: 1,
            featuredUser1Id: 'BBBBBB',
            featuredUser2Id: 'BBBBBB',
            featuredUser3Id: 'BBBBBB',
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            highestPostNumber: 1,
            likeCount: 1,
            incomingLinkCount: 1,
            categoryId: 1,
            visible: true,
            moderatorPostsCount: 1,
            closed: true,
            archived: true,
            bumpedAt: currentDate.format(DATE_TIME_FORMAT),
            hasSummary: true,
            archetype: 'BBBBBB',
            featuredUser4Id: 'BBBBBB',
            notifyModeratorsCount: 1,
            spamCount: 1,
            pinnedAt: currentDate.format(DATE_TIME_FORMAT),
            score: 1,
            percentRank: 1,
            subtype: 'BBBBBB',
            slug: 'BBBBBB',
            deletedById: 'BBBBBB',
            participantCount: 1,
            wordCount: 1,
            excerpt: 'BBBBBB',
            pinnedGlobally: true,
            pinnedUntil: currentDate.format(DATE_TIME_FORMAT),
            fancyTitle: 'BBBBBB',
            highestStaffPostNumber: 1,
            featuredLink: 'BBBBBB',
            reviewableScore: 1,
            imageUploadId: 1,
            slowModeSeconds: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastPostedAt: currentDate,
            deletedAt: currentDate,
            bumpedAt: currentDate,
            pinnedAt: currentDate,
            pinnedUntil: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Topics', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            lastPostedAt: currentDate.format(DATE_TIME_FORMAT),
            views: 1,
            postsCount: 1,
            userId: 'BBBBBB',
            lastPostUserId: 1,
            replyCount: 1,
            featuredUser1Id: 'BBBBBB',
            featuredUser2Id: 'BBBBBB',
            featuredUser3Id: 'BBBBBB',
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            highestPostNumber: 1,
            likeCount: 1,
            incomingLinkCount: 1,
            categoryId: 1,
            visible: true,
            moderatorPostsCount: 1,
            closed: true,
            archived: true,
            bumpedAt: currentDate.format(DATE_TIME_FORMAT),
            hasSummary: true,
            archetype: 'BBBBBB',
            featuredUser4Id: 'BBBBBB',
            notifyModeratorsCount: 1,
            spamCount: 1,
            pinnedAt: currentDate.format(DATE_TIME_FORMAT),
            score: 1,
            percentRank: 1,
            subtype: 'BBBBBB',
            slug: 'BBBBBB',
            deletedById: 'BBBBBB',
            participantCount: 1,
            wordCount: 1,
            excerpt: 'BBBBBB',
            pinnedGlobally: true,
            pinnedUntil: currentDate.format(DATE_TIME_FORMAT),
            fancyTitle: 'BBBBBB',
            highestStaffPostNumber: 1,
            featuredLink: 'BBBBBB',
            reviewableScore: 1,
            imageUploadId: 1,
            slowModeSeconds: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastPostedAt: currentDate,
            deletedAt: currentDate,
            bumpedAt: currentDate,
            pinnedAt: currentDate,
            pinnedUntil: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Topics', () => {
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
