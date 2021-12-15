import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { WebHooksService } from 'app/entities/web-hooks/web-hooks.service';
import { IWebHooks, WebHooks } from 'app/shared/model/web-hooks.model';

describe('Service Tests', () => {
  describe('WebHooks Service', () => {
    let injector: TestBed;
    let service: WebHooksService;
    let httpMock: HttpTestingController;
    let elemDefault: IWebHooks;
    let expectedResult: IWebHooks | IWebHooks[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WebHooksService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new WebHooks(0, 'AAAAAAA', 0, 0, 0, 'AAAAAAA', false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WebHooks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new WebHooks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WebHooks', () => {
        const returnedFromService = Object.assign(
          {
            payloadUrl: 'BBBBBB',
            contentType: 1,
            lastDeliveryStatus: 1,
            status: 1,
            secret: 'BBBBBB',
            wildcardWebHook: true,
            verifyCertificate: true,
            active: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WebHooks', () => {
        const returnedFromService = Object.assign(
          {
            payloadUrl: 'BBBBBB',
            contentType: 1,
            lastDeliveryStatus: 1,
            status: 1,
            secret: 'BBBBBB',
            wildcardWebHook: true,
            verifyCertificate: true,
            active: true,
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

      it('should delete a WebHooks', () => {
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
