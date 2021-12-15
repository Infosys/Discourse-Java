import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PostActionsService } from 'app/entities/post-actions/post-actions.service';
import { IPostActions, PostActions } from 'app/shared/model/post-actions.model';

describe('Service Tests', () => {
  describe('PostActions Service', () => {
    let injector: TestBed;
    let service: PostActionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPostActions;
    let expectedResult: IPostActions | IPostActions[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PostActionsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PostActions(
        0,
        0,
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        0,
        false,
        'AAAAAAA',
        false,
        currentDate,
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            agreedAt: currentDate.format(DATE_TIME_FORMAT),
            deferredAt: currentDate.format(DATE_TIME_FORMAT),
            disagreedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PostActions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            agreedAt: currentDate.format(DATE_TIME_FORMAT),
            deferredAt: currentDate.format(DATE_TIME_FORMAT),
            disagreedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
            agreedAt: currentDate,
            deferredAt: currentDate,
            disagreedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new PostActions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PostActions', () => {
        const returnedFromService = Object.assign(
          {
            postId: 1,
            userId: 'BBBBBB',
            postActionTypeId: 1,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
            relatedPostId: 1,
            staffTookAction: true,
            deferredById: 'BBBBBB',
            targetsTopic: true,
            agreedAt: currentDate.format(DATE_TIME_FORMAT),
            agreedById: 'BBBBBB',
            deferredAt: currentDate.format(DATE_TIME_FORMAT),
            disagreedAt: currentDate.format(DATE_TIME_FORMAT),
            disagreedById: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
            agreedAt: currentDate,
            deferredAt: currentDate,
            disagreedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PostActions', () => {
        const returnedFromService = Object.assign(
          {
            postId: 1,
            userId: 'BBBBBB',
            postActionTypeId: 1,
            deletedAt: currentDate.format(DATE_TIME_FORMAT),
            deletedById: 'BBBBBB',
            relatedPostId: 1,
            staffTookAction: true,
            deferredById: 'BBBBBB',
            targetsTopic: true,
            agreedAt: currentDate.format(DATE_TIME_FORMAT),
            agreedById: 'BBBBBB',
            deferredAt: currentDate.format(DATE_TIME_FORMAT),
            disagreedAt: currentDate.format(DATE_TIME_FORMAT),
            disagreedById: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            deletedAt: currentDate,
            agreedAt: currentDate,
            deferredAt: currentDate,
            disagreedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PostActions', () => {
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
