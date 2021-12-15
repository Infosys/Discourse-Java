import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PostRevisionsService } from 'app/entities/post-revisions/post-revisions.service';
import { IPostRevisions, PostRevisions } from 'app/shared/model/post-revisions.model';

describe('Service Tests', () => {
  describe('PostRevisions Service', () => {
    let injector: TestBed;
    let service: PostRevisionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPostRevisions;
    let expectedResult: IPostRevisions | IPostRevisions[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PostRevisionsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PostRevisions(0, 'AAAAAAA', 0, 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PostRevisions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PostRevisions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PostRevisions', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            postId: 1,
            modifications: 'BBBBBB',
            number: 1,
            hidden: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PostRevisions', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            postId: 1,
            modifications: 'BBBBBB',
            number: 1,
            hidden: true,
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

      it('should delete a PostRevisions', () => {
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
