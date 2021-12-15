import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TopicLinkClicksService } from 'app/entities/topic-link-clicks/topic-link-clicks.service';
import { ITopicLinkClicks, TopicLinkClicks } from 'app/shared/model/topic-link-clicks.model';

describe('Service Tests', () => {
  describe('TopicLinkClicks Service', () => {
    let injector: TestBed;
    let service: TopicLinkClicksService;
    let httpMock: HttpTestingController;
    let elemDefault: ITopicLinkClicks;
    let expectedResult: ITopicLinkClicks | ITopicLinkClicks[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TopicLinkClicksService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TopicLinkClicks(0, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TopicLinkClicks', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TopicLinkClicks()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TopicLinkClicks', () => {
        const returnedFromService = Object.assign(
          {
            topicLinkId: 1,
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TopicLinkClicks', () => {
        const returnedFromService = Object.assign(
          {
            topicLinkId: 1,
            userId: 'BBBBBB',
            ipAddress: 'BBBBBB',
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

      it('should delete a TopicLinkClicks', () => {
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
