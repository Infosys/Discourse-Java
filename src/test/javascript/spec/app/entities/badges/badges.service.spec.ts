import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BadgesService } from 'app/entities/badges/badges.service';
import { IBadges, Badges } from 'app/shared/model/badges.model';

describe('Service Tests', () => {
  describe('Badges Service', () => {
    let injector: TestBed;
    let service: BadgesService;
    let httpMock: HttpTestingController;
    let elemDefault: IBadges;
    let expectedResult: IBadges | IBadges[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BadgesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Badges(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        false,
        false,
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        false,
        false,
        0,
        0,
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Badges', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Badges()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Badges', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            badgeTypeId: 1,
            grantCount: 1,
            allowTitle: true,
            multipleGrant: true,
            icon: 'BBBBBB',
            listable: true,
            targetPosts: true,
            query: 'BBBBBB',
            enabled: true,
            autoRevoke: true,
            badgeGroupingId: 1,
            trigger: 1,
            showPosts: true,
            system: true,
            image: 'BBBBBB',
            longDescription: 'BBBBBB',
            imageUploadId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Badges', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            badgeTypeId: 1,
            grantCount: 1,
            allowTitle: true,
            multipleGrant: true,
            icon: 'BBBBBB',
            listable: true,
            targetPosts: true,
            query: 'BBBBBB',
            enabled: true,
            autoRevoke: true,
            badgeGroupingId: 1,
            trigger: 1,
            showPosts: true,
            system: true,
            image: 'BBBBBB',
            longDescription: 'BBBBBB',
            imageUploadId: 1,
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

      it('should delete a Badges', () => {
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
