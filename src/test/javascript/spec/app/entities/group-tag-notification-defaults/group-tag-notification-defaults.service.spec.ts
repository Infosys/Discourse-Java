import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GroupTagNotificationDefaultsService } from 'app/entities/group-tag-notification-defaults/group-tag-notification-defaults.service';
import { IGroupTagNotificationDefaults, GroupTagNotificationDefaults } from 'app/shared/model/group-tag-notification-defaults.model';

describe('Service Tests', () => {
  describe('GroupTagNotificationDefaults Service', () => {
    let injector: TestBed;
    let service: GroupTagNotificationDefaultsService;
    let httpMock: HttpTestingController;
    let elemDefault: IGroupTagNotificationDefaults;
    let expectedResult: IGroupTagNotificationDefaults | IGroupTagNotificationDefaults[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GroupTagNotificationDefaultsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GroupTagNotificationDefaults(0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GroupTagNotificationDefaults', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GroupTagNotificationDefaults()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GroupTagNotificationDefaults', () => {
        const returnedFromService = Object.assign(
          {
            groupId: 1,
            tagId: 1,
            notificationLevel: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GroupTagNotificationDefaults', () => {
        const returnedFromService = Object.assign(
          {
            groupId: 1,
            tagId: 1,
            notificationLevel: 1,
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

      it('should delete a GroupTagNotificationDefaults', () => {
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
