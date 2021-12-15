import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DirectoryItemsService } from 'app/entities/directory-items/directory-items.service';
import { IDirectoryItems, DirectoryItems } from 'app/shared/model/directory-items.model';

describe('Service Tests', () => {
  describe('DirectoryItems Service', () => {
    let injector: TestBed;
    let service: DirectoryItemsService;
    let httpMock: HttpTestingController;
    let elemDefault: IDirectoryItems;
    let expectedResult: IDirectoryItems | IDirectoryItems[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DirectoryItemsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DirectoryItems(0, 0, 'AAAAAAA', 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DirectoryItems', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DirectoryItems()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DirectoryItems', () => {
        const returnedFromService = Object.assign(
          {
            periodType: 1,
            userId: 'BBBBBB',
            likesReceived: 1,
            likesGiven: 1,
            topicsEntered: 1,
            topicCount: 1,
            postCount: 1,
            daysVisited: 1,
            postsRead: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DirectoryItems', () => {
        const returnedFromService = Object.assign(
          {
            periodType: 1,
            userId: 'BBBBBB',
            likesReceived: 1,
            likesGiven: 1,
            topicsEntered: 1,
            topicCount: 1,
            postCount: 1,
            daysVisited: 1,
            postsRead: 1,
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

      it('should delete a DirectoryItems', () => {
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
