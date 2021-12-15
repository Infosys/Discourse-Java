import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { WebHookEventsService } from 'app/entities/web-hook-events/web-hook-events.service';
import { IWebHookEvents, WebHookEvents } from 'app/shared/model/web-hook-events.model';

describe('Service Tests', () => {
  describe('WebHookEvents Service', () => {
    let injector: TestBed;
    let service: WebHookEventsService;
    let httpMock: HttpTestingController;
    let elemDefault: IWebHookEvents;
    let expectedResult: IWebHookEvents | IWebHookEvents[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(WebHookEventsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new WebHookEvents(0, 0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a WebHookEvents', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new WebHookEvents()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a WebHookEvents', () => {
        const returnedFromService = Object.assign(
          {
            webHookId: 1,
            headers: 'BBBBBB',
            payload: 'BBBBBB',
            status: 1,
            responseHeaders: 'BBBBBB',
            responseBody: 'BBBBBB',
            duration: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of WebHookEvents', () => {
        const returnedFromService = Object.assign(
          {
            webHookId: 1,
            headers: 'BBBBBB',
            payload: 'BBBBBB',
            status: 1,
            responseHeaders: 'BBBBBB',
            responseBody: 'BBBBBB',
            duration: 1,
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

      it('should delete a WebHookEvents', () => {
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
