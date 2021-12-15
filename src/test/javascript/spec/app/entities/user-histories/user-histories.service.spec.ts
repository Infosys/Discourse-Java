import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserHistoriesService } from 'app/entities/user-histories/user-histories.service';
import { IUserHistories, UserHistories } from 'app/shared/model/user-histories.model';

describe('Service Tests', () => {
  describe('UserHistories Service', () => {
    let injector: TestBed;
    let service: UserHistoriesService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserHistories;
    let expectedResult: IUserHistories | IUserHistories[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserHistoriesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserHistories(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        false,
        0,
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserHistories', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserHistories()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserHistories', () => {
        const returnedFromService = Object.assign(
          {
            action: 1,
            actingUserId: 'BBBBBB',
            targetUserId: 'BBBBBB',
            details: 'BBBBBB',
            context: 'BBBBBB',
            ipAddress: 'BBBBBB',
            email: 'BBBBBB',
            subject: 'BBBBBB',
            previousValue: 'BBBBBB',
            newValue: 'BBBBBB',
            topicId: 1,
            adminOnly: true,
            postId: 1,
            customType: 'BBBBBB',
            categoryId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserHistories', () => {
        const returnedFromService = Object.assign(
          {
            action: 1,
            actingUserId: 'BBBBBB',
            targetUserId: 'BBBBBB',
            details: 'BBBBBB',
            context: 'BBBBBB',
            ipAddress: 'BBBBBB',
            email: 'BBBBBB',
            subject: 'BBBBBB',
            previousValue: 'BBBBBB',
            newValue: 'BBBBBB',
            topicId: 1,
            adminOnly: true,
            postId: 1,
            customType: 'BBBBBB',
            categoryId: 1,
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

      it('should delete a UserHistories', () => {
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
