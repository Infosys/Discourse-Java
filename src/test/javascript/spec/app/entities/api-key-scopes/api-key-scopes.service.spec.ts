import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApiKeyScopesService } from 'app/entities/api-key-scopes/api-key-scopes.service';
import { IApiKeyScopes, ApiKeyScopes } from 'app/shared/model/api-key-scopes.model';

describe('Service Tests', () => {
  describe('ApiKeyScopes Service', () => {
    let injector: TestBed;
    let service: ApiKeyScopesService;
    let httpMock: HttpTestingController;
    let elemDefault: IApiKeyScopes;
    let expectedResult: IApiKeyScopes | IApiKeyScopes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ApiKeyScopesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ApiKeyScopes(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ApiKeyScopes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ApiKeyScopes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ApiKeyScopes', () => {
        const returnedFromService = Object.assign(
          {
            apiKeyId: 1,
            resource: 'BBBBBB',
            action: 'BBBBBB',
            allowedParameters: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ApiKeyScopes', () => {
        const returnedFromService = Object.assign(
          {
            apiKeyId: 1,
            resource: 'BBBBBB',
            action: 'BBBBBB',
            allowedParameters: 'BBBBBB',
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

      it('should delete a ApiKeyScopes', () => {
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
