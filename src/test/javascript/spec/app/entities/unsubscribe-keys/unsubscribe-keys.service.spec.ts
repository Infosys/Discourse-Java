import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UnsubscribeKeysService } from 'app/entities/unsubscribe-keys/unsubscribe-keys.service';
import { IUnsubscribeKeys, UnsubscribeKeys } from 'app/shared/model/unsubscribe-keys.model';

describe('Service Tests', () => {
  describe('UnsubscribeKeys Service', () => {
    let injector: TestBed;
    let service: UnsubscribeKeysService;
    let httpMock: HttpTestingController;
    let elemDefault: IUnsubscribeKeys;
    let expectedResult: IUnsubscribeKeys | IUnsubscribeKeys[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UnsubscribeKeysService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UnsubscribeKeys(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UnsubscribeKeys', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UnsubscribeKeys()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UnsubscribeKeys', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            userId: 'BBBBBB',
            unsubscribeKeyType: 'BBBBBB',
            topicId: 1,
            postId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UnsubscribeKeys', () => {
        const returnedFromService = Object.assign(
          {
            key: 'BBBBBB',
            userId: 'BBBBBB',
            unsubscribeKeyType: 'BBBBBB',
            topicId: 1,
            postId: 1,
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

      it('should delete a UnsubscribeKeys', () => {
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
