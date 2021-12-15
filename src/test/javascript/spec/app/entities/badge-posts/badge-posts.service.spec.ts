import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BadgePostsService } from 'app/entities/badge-posts/badge-posts.service';
import { IBadgePosts, BadgePosts } from 'app/shared/model/badge-posts.model';

describe('Service Tests', () => {
  describe('BadgePosts Service', () => {
    let injector: TestBed;
    let service: BadgePostsService;
    let httpMock: HttpTestingController;
    let elemDefault: IBadgePosts;
    let expectedResult: IBadgePosts | IBadgePosts[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BadgePostsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BadgePosts(
        0,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        currentDate,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        false,
        0,
        0,
        0,
        0,
        0,
        currentDate,
        false,
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        false,
        currentDate,
        0,
        currentDate,
        0,
        false,
        false,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            lastVersionAt: currentDate.format(DATE_TIME_FORMAT),
            bakedAt: currentDate.format(DATE_TIME_FORMAT),
            hiddenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BadgePosts', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            lastVersionAt: currentDate.format(DATE_TIME_FORMAT),
            bakedAt: currentDate.format(DATE_TIME_FORMAT),
            hiddenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
            lastVersionAt: currentDate,
            bakedAt: currentDate,
            hiddenAt: currentDate,
          },
          returnedFromService
        );

        service.create(new BadgePosts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BadgePosts', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicId: 1,
            postNumber: 1,
            raw: 'BBBBBB',
            cooked: 'BBBBBB',
            replyToPostNumber: 1,
            replyCount: 1,
            quoteCount: 1,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            offTopicCount: 1,
            likeCount: 1,
            incomingLinkCount: 1,
            bookmarkCount: 1,
            score: 1,
            reads: 1,
            postType: 1,
            sortOrder: 1,
            lastEditorId: 'BBBBBB',
            hidden: true,
            hiddenReasonId: 1,
            notifyModeratorsCount: 1,
            spamCount: 1,
            illegalCount: 1,
            inappropriateCount: 1,
            lastVersionAt: currentDate.format(DATE_TIME_FORMAT),
            userDeleted: true,
            replyToUserId: 'BBBBBB',
            percentRank: 1,
            notifyUserCount: 1,
            likeScore: 1,
            deletedById: 'BBBBBB',
            editReason: 'BBBBBB',
            wordCount: 1,
            version: 1,
            cookMethod: 1,
            wiki: true,
            bakedAt: currentDate.format(DATE_TIME_FORMAT),
            bakedVersion: 1,
            hiddenAt: currentDate.format(DATE_TIME_FORMAT),
            selfEdits: 1,
            replyQuoted: true,
            viaEmail: true,
            rawEmail: 'BBBBBB',
            publicVersion: 1,
            actionCode: 'BBBBBB',
            lockedById: 'BBBBBB',
            imageUploadId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
            lastVersionAt: currentDate,
            bakedAt: currentDate,
            hiddenAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BadgePosts', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            topicId: 1,
            postNumber: 1,
            raw: 'BBBBBB',
            cooked: 'BBBBBB',
            replyToPostNumber: 1,
            replyCount: 1,
            quoteCount: 1,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            offTopicCount: 1,
            likeCount: 1,
            incomingLinkCount: 1,
            bookmarkCount: 1,
            score: 1,
            reads: 1,
            postType: 1,
            sortOrder: 1,
            lastEditorId: 'BBBBBB',
            hidden: true,
            hiddenReasonId: 1,
            notifyModeratorsCount: 1,
            spamCount: 1,
            illegalCount: 1,
            inappropriateCount: 1,
            lastVersionAt: currentDate.format(DATE_TIME_FORMAT),
            userDeleted: true,
            replyToUserId: 'BBBBBB',
            percentRank: 1,
            notifyUserCount: 1,
            likeScore: 1,
            deletedById: 'BBBBBB',
            editReason: 'BBBBBB',
            wordCount: 1,
            version: 1,
            cookMethod: 1,
            wiki: true,
            bakedAt: currentDate.format(DATE_TIME_FORMAT),
            bakedVersion: 1,
            hiddenAt: currentDate.format(DATE_TIME_FORMAT),
            selfEdits: 1,
            replyQuoted: true,
            viaEmail: true,
            rawEmail: 'BBBBBB',
            publicVersion: 1,
            actionCode: 'BBBBBB',
            lockedById: 'BBBBBB',
            imageUploadId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
            lastVersionAt: currentDate,
            bakedAt: currentDate,
            hiddenAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BadgePosts', () => {
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
