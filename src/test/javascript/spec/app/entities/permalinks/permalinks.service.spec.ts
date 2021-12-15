import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PermalinksService } from 'app/entities/permalinks/permalinks.service';
import { IPermalinks, Permalinks } from 'app/shared/model/permalinks.model';

describe('Service Tests', () => {
  describe('Permalinks Service', () => {
    let injector: TestBed;
    let service: PermalinksService;
    let httpMock: HttpTestingController;
    let elemDefault: IPermalinks;
    let expectedResult: IPermalinks | IPermalinks[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PermalinksService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Permalinks(0, 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Permalinks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Permalinks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Permalinks', () => {
        const returnedFromService = Object.assign(
          {
            url: 'BBBBBB',
            topicId: 1,
            postId: 1,
            categoryId: 1,
            externalUrl: 'BBBBBB',
            tagId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Permalinks', () => {
        const returnedFromService = Object.assign(
          {
            url: 'BBBBBB',
            topicId: 1,
            postId: 1,
            categoryId: 1,
            externalUrl: 'BBBBBB',
            tagId: 1,
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

      it('should delete a Permalinks', () => {
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
