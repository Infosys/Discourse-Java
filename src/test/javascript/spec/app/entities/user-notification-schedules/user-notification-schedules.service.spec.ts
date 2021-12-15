import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserNotificationSchedulesService } from 'app/entities/user-notification-schedules/user-notification-schedules.service';
import { IUserNotificationSchedules, UserNotificationSchedules } from 'app/shared/model/user-notification-schedules.model';

describe('Service Tests', () => {
  describe('UserNotificationSchedules Service', () => {
    let injector: TestBed;
    let service: UserNotificationSchedulesService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserNotificationSchedules;
    let expectedResult: IUserNotificationSchedules | IUserNotificationSchedules[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserNotificationSchedulesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserNotificationSchedules(0, 'AAAAAAA', false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserNotificationSchedules', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserNotificationSchedules()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserNotificationSchedules', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            enabled: true,
            day0StartTime: 1,
            day0EndTime: 1,
            day1StartTime: 1,
            day1EndTime: 1,
            day2StartTime: 1,
            day2EndTime: 1,
            day3StartTime: 1,
            day3EndTime: 1,
            day4StartTime: 1,
            day4EndTime: 1,
            day5StartTime: 1,
            day5EndTime: 1,
            day6StartTime: 1,
            day6EndTime: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserNotificationSchedules', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            enabled: true,
            day0StartTime: 1,
            day0EndTime: 1,
            day1StartTime: 1,
            day1EndTime: 1,
            day2StartTime: 1,
            day2EndTime: 1,
            day3StartTime: 1,
            day3EndTime: 1,
            day4StartTime: 1,
            day4EndTime: 1,
            day5StartTime: 1,
            day5EndTime: 1,
            day6StartTime: 1,
            day6EndTime: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserNotificationSchedules', () => {
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
