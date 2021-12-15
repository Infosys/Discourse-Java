import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { WebHookEventTypesHooksService } from 'app/entities/web-hook-event-types-hooks/web-hook-event-types-hooks.service';
import { IWebHookEventTypesHooks, WebHookEventTypesHooks } from 'app/shared/model/web-hook-event-types-hooks.model';

describe('Service Tests', () => {
  describe('WebHookEventTypesHooks Service', () => {
    let injector: TestBed;
    let service: WebHookEventTypesHooksService;
    let httpMock: HttpTestingController;
    let elemDefault: IWebHookEventTypesHooks;
    let expectedResult: IWebHookEventTypesHooks | IWebHookEventTypesHooks[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WebHookEventTypesHooksService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new WebHookEventTypesHooks(0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WebHookEventTypesHooks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new WebHookEventTypesHooks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WebHookEventTypesHooks', () => {
        const returnedFromService = Object.assign(
          {
            webHookId: 1,
            webHookEventTypeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WebHookEventTypesHooks', () => {
        const returnedFromService = Object.assign(
          {
            webHookId: 1,
            webHookEventTypeId: 1,
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

      it('should delete a WebHookEventTypesHooks', () => {
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
