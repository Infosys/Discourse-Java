import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserActionsService } from 'app/entities/user-actions/user-actions.service';
import { IUserActions, UserActions } from 'app/shared/model/user-actions.model';

describe('Service Tests', () => {
  describe('UserActions Service', () => {
    let injector: TestBed;
    let service: UserActionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserActions;
    let expectedResult: IUserActions | IUserActions[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserActionsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserActions(0, 0, 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserActions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserActions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserActions', () => {
        const returnedFromService = Object.assign(
          {
            actionType: 1,
            userId: 'BBBBBB',
            targetTopicId: 1,
            targetPostId: 1,
            targetUserId: 'BBBBBB',
            actingUserId: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserActions', () => {
        const returnedFromService = Object.assign(
          {
            actionType: 1,
            userId: 'BBBBBB',
            targetTopicId: 1,
            targetPostId: 1,
            targetUserId: 'BBBBBB',
            actingUserId: 'BBBBBB',
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

      it('should delete a UserActions', () => {
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
