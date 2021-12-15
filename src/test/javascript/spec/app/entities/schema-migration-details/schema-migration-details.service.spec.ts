import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SchemaMigrationDetailsService } from 'app/entities/schema-migration-details/schema-migration-details.service';
import { ISchemaMigrationDetails, SchemaMigrationDetails } from 'app/shared/model/schema-migration-details.model';

describe('Service Tests', () => {
  describe('SchemaMigrationDetails Service', () => {
    let injector: TestBed;
    let service: SchemaMigrationDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchemaMigrationDetails;
    let expectedResult: ISchemaMigrationDetails | ISchemaMigrationDetails[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SchemaMigrationDetailsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SchemaMigrationDetails(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SchemaMigrationDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SchemaMigrationDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SchemaMigrationDetails', () => {
        const returnedFromService = Object.assign(
          {
            version: 'BBBBBB',
            name: 'BBBBBB',
            hostname: 'BBBBBB',
            gitVersion: 'BBBBBB',
            railsVersion: 'BBBBBB',
            duration: 1,
            direction: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SchemaMigrationDetails', () => {
        const returnedFromService = Object.assign(
          {
            version: 'BBBBBB',
            name: 'BBBBBB',
            hostname: 'BBBBBB',
            gitVersion: 'BBBBBB',
            railsVersion: 'BBBBBB',
            duration: 1,
            direction: 'BBBBBB',
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

      it('should delete a SchemaMigrationDetails', () => {
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
