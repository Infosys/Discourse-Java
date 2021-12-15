import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NotificationsService } from 'app/entities/notifications/notifications.service';
import { INotifications, Notifications } from 'app/shared/model/notifications.model';

describe('Service Tests', () => {
  describe('Notifications Service', () => {
    let injector: TestBed;
    let service: NotificationsService;
    let httpMock: HttpTestingController;
    let elemDefault: INotifications;
    let expectedResult: INotifications | INotifications[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NotificationsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Notifications(0, 0, 'AAAAAAA', 'AAAAAAA', false, 0, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Notifications', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Notifications()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Notifications', () => {
        const returnedFromService = Object.assign(
          {
            notificationType: 1,
            userId: 'BBBBBB',
            data: 'BBBBBB',
            read: true,
            topicId: 1,
            postNumber: 1,
            postActionId: 1,
            highPriority: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Notifications', () => {
        const returnedFromService = Object.assign(
          {
            notificationType: 1,
            userId: 'BBBBBB',
            data: 'BBBBBB',
            read: true,
            topicId: 1,
            postNumber: 1,
            postActionId: 1,
            highPriority: true,
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

      it('should delete a Notifications', () => {
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
