import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PollsService } from 'app/entities/polls/polls.service';
import { IPolls, Polls } from 'app/shared/model/polls.model';

describe('Service Tests', () => {
  describe('Polls Service', () => {
    let injector: TestBed;
    let service: PollsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPolls;
    let expectedResult: IPolls | IPolls[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PollsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Polls(0, 0, 'AAAAAAA', currentDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            closeAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Polls', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            closeAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            closeAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Polls()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Polls', () => {
        const returnedFromService = Object.assign(
          {
            postId: 1,
            name: 'BBBBBB',
            closeAt: currentDate.format(DATE_TIME_FORMAT),
            type: 1,
            status: 1,
            results: 1,
            visibility: 1,
            min: 1,
            max: 1,
            step: 1,
            anonymousVoters: 1,
            chartType: 1,
            groups: 'BBBBBB',
            title: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            closeAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Polls', () => {
        const returnedFromService = Object.assign(
          {
            postId: 1,
            name: 'BBBBBB',
            closeAt: currentDate.format(DATE_TIME_FORMAT),
            type: 1,
            status: 1,
            results: 1,
            visibility: 1,
            min: 1,
            max: 1,
            step: 1,
            anonymousVoters: 1,
            chartType: 1,
            groups: 'BBBBBB',
            title: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            closeAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Polls', () => {
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
