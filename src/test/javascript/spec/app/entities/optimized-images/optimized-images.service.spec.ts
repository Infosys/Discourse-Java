import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { OptimizedImagesService } from 'app/entities/optimized-images/optimized-images.service';
import { IOptimizedImages, OptimizedImages } from 'app/shared/model/optimized-images.model';

describe('Service Tests', () => {
  describe('OptimizedImages Service', () => {
    let injector: TestBed;
    let service: OptimizedImagesService;
    let httpMock: HttpTestingController;
    let elemDefault: IOptimizedImages;
    let expectedResult: IOptimizedImages | IOptimizedImages[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OptimizedImagesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new OptimizedImages(0, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OptimizedImages', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new OptimizedImages()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OptimizedImages', () => {
        const returnedFromService = Object.assign(
          {
            sha1: 'BBBBBB',
            extension: 'BBBBBB',
            width: 1,
            height: 1,
            uploadId: 1,
            url: 'BBBBBB',
            filesize: 1,
            etag: 'BBBBBB',
            version: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OptimizedImages', () => {
        const returnedFromService = Object.assign(
          {
            sha1: 'BBBBBB',
            extension: 'BBBBBB',
            width: 1,
            height: 1,
            uploadId: 1,
            url: 'BBBBBB',
            filesize: 1,
            etag: 'BBBBBB',
            version: 1,
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

      it('should delete a OptimizedImages', () => {
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
