import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GroupHistoriesService } from 'app/entities/group-histories/group-histories.service';
import { IGroupHistories, GroupHistories } from 'app/shared/model/group-histories.model';

describe('Service Tests', () => {
  describe('GroupHistories Service', () => {
    let injector: TestBed;
    let service: GroupHistoriesService;
    let httpMock: HttpTestingController;
    let elemDefault: IGroupHistories;
    let expectedResult: IGroupHistories | IGroupHistories[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GroupHistoriesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GroupHistories(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GroupHistories', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new GroupHistories()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GroupHistories', () => {
        const returnedFromService = Object.assign(
          {
            groupId: 1,
            actingUserId: 'BBBBBB',
            targetUserId: 'BBBBBB',
            action: 1,
            subject: 'BBBBBB',
            prevValue: 'BBBBBB',
            newValue: 'BBBBBB',
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GroupHistories', () => {
        const returnedFromService = Object.assign(
          {
            groupId: 1,
            actingUserId: 'BBBBBB',
            targetUserId: 'BBBBBB',
            action: 1,
            subject: 'BBBBBB',
            prevValue: 'BBBBBB',
            newValue: 'BBBBBB',
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GroupHistories', () => {
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
