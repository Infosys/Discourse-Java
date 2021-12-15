import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { IncomingLinksService } from 'app/entities/incoming-links/incoming-links.service';
import { IIncomingLinks, IncomingLinks } from 'app/shared/model/incoming-links.model';

describe('Service Tests', () => {
  describe('IncomingLinks Service', () => {
    let injector: TestBed;
    let service: IncomingLinksService;
    let httpMock: HttpTestingController;
    let elemDefault: IIncomingLinks;
    let expectedResult: IIncomingLinks | IIncomingLinks[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IncomingLinksService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new IncomingLinks(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IncomingLinks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new IncomingLinks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IncomingLinks', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
            currentUserId: 'BBBBBB',
            postId: 1,
            incomingRefererId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IncomingLinks', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
            currentUserId: 'BBBBBB',
            postId: 1,
            incomingRefererId: 1,
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

      it('should delete a IncomingLinks', () => {
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
