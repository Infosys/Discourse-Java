import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TopicThumbnailsService } from 'app/entities/topic-thumbnails/topic-thumbnails.service';
import { ITopicThumbnails, TopicThumbnails } from 'app/shared/model/topic-thumbnails.model';

describe('Service Tests', () => {
  describe('TopicThumbnails Service', () => {
    let injector: TestBed;
    let service: TopicThumbnailsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicThumbnails;
    let expectedResult: ITopicThumbnails | ITopicThumbnails[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicThumbnailsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TopicThumbnails(0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicThumbnails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TopicThumbnails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicThumbnails', () => {
        const returnedFromService = Object.assign(
          {
            uploadId: 1,
            optimizedImageId: 1,
            maxWidth: 1,
            maxHeight: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopicThumbnails', () => {
        const returnedFromService = Object.assign(
          {
            uploadId: 1,
            optimizedImageId: 1,
            maxWidth: 1,
            maxHeight: 1,
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

      it('should delete a TopicThumbnails', () => {
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
