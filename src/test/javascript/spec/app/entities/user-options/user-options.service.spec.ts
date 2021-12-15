import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserOptionsService } from 'app/entities/user-options/user-options.service';
import { IUserOptions, UserOptions } from 'app/shared/model/user-options.model';

describe('Service Tests', () => {
  describe('UserOptions Service', () => {
    let injector: TestBed;
    let service: UserOptionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserOptions;
    let expectedResult: IUserOptions | IUserOptions[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserOptionsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserOptions(
        0,
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        0,
        0,
        0,
        currentDate,
        0,
        false,
        0,
        0,
        false,
        0,
        0,
        false,
        0,
        0,
        false,
        0,
        0,
        0,
        0,
        0,
        false,
        'AAAAAAA',
        false,
        0,
        false,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastRedirectedToTopAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserOptions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastRedirectedToTopAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastRedirectedToTopAt: currentDate,
          },
          returnedFromService
        );

        service.create(new UserOptions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserOptions', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            mailingListMode: true,
            emailDigests: true,
            externalLinksInNewTab: true,
            enableQuoting: true,
            dynamicFavicon: true,
            disableJumpReply: true,
            automaticallyUnpinTopics: true,
            digestAfterMinutes: 1,
            autoTrackTopicsAfterMsecs: 1,
            newTopicDurationMinutes: 1,
            lastRedirectedToTopAt: currentDate.format(DATE_TIME_FORMAT),
            emailPreviousReplies: 1,
            emailInReplyTo: true,
            likeNotificationFrequency: 1,
            mailingListModeFrequency: 1,
            includeTl0InDigests: true,
            notificationLevelWhenReplying: 1,
            themeKeySeq: 1,
            allowPrivateMessages: true,
            homepageId: 1,
            themeIds: 1,
            hideProfileAndPresence: true,
            textSizeKey: 1,
            textSizeSeq: 1,
            emailLevel: 1,
            emailMessagesLevel: 1,
            titleCountModeKey: 1,
            enableDefer: true,
            timezone: 'BBBBBB',
            enableAllowedPmUsers: true,
            darkSchemeId: 1,
            skipNewUserTips: true,
            colorSchemeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastRedirectedToTopAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserOptions', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            mailingListMode: true,
            emailDigests: true,
            externalLinksInNewTab: true,
            enableQuoting: true,
            dynamicFavicon: true,
            disableJumpReply: true,
            automaticallyUnpinTopics: true,
            digestAfterMinutes: 1,
            autoTrackTopicsAfterMsecs: 1,
            newTopicDurationMinutes: 1,
            lastRedirectedToTopAt: currentDate.format(DATE_TIME_FORMAT),
            emailPreviousReplies: 1,
            emailInReplyTo: true,
            likeNotificationFrequency: 1,
            mailingListModeFrequency: 1,
            includeTl0InDigests: true,
            notificationLevelWhenReplying: 1,
            themeKeySeq: 1,
            allowPrivateMessages: true,
            homepageId: 1,
            themeIds: 1,
            hideProfileAndPresence: true,
            textSizeKey: 1,
            textSizeSeq: 1,
            emailLevel: 1,
            emailMessagesLevel: 1,
            titleCountModeKey: 1,
            enableDefer: true,
            timezone: 'BBBBBB',
            enableAllowedPmUsers: true,
            darkSchemeId: 1,
            skipNewUserTips: true,
            colorSchemeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastRedirectedToTopAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserOptions', () => {
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
