import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SingleSignOnRecordsService } from 'app/entities/single-sign-on-records/single-sign-on-records.service';
import { ISingleSignOnRecords, SingleSignOnRecords } from 'app/shared/model/single-sign-on-records.model';

describe('Service Tests', () => {
  describe('SingleSignOnRecords Service', () => {
    let injector: TestBed;
    let service: SingleSignOnRecordsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISingleSignOnRecords;
    let expectedResult: ISingleSignOnRecords | ISingleSignOnRecords[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SingleSignOnRecordsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SingleSignOnRecords(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
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

      it('should create a SingleSignOnRecords', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SingleSignOnRecords()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SingleSignOnRecords', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            externalId: 'BBBBBB',
            lastPayload: 'BBBBBB',
            externalUsername: 'BBBBBB',
            externalEmail: 'BBBBBB',
            externalName: 'BBBBBB',
            externalAvatarUrl: 'BBBBBB',
            externalProfileBackgroundUrl: 'BBBBBB',
            externalCardBackgroundUrl: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SingleSignOnRecords', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            externalId: 'BBBBBB',
            lastPayload: 'BBBBBB',
            externalUsername: 'BBBBBB',
            externalEmail: 'BBBBBB',
            externalName: 'BBBBBB',
            externalAvatarUrl: 'BBBBBB',
            externalProfileBackgroundUrl: 'BBBBBB',
            externalCardBackgroundUrl: 'BBBBBB',
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

      it('should delete a SingleSignOnRecords', () => {
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
