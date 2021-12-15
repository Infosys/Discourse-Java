import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserProfilesService } from 'app/entities/user-profiles/user-profiles.service';
import { IUserProfiles, UserProfiles } from 'app/shared/model/user-profiles.model';

describe('Service Tests', () => {
  describe('UserProfiles Service', () => {
    let injector: TestBed;
    let service: UserProfilesService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserProfiles;
    let expectedResult: IUserProfiles | IUserProfiles[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserProfilesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserProfiles(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, false, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserProfiles', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserProfiles()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserProfiles', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            location: 'BBBBBB',
            website: 'BBBBBB',
            bioRaw: 'BBBBBB',
            bioCooked: 'BBBBBB',
            dismissedBannerKey: 1,
            bioCookedVersion: 1,
            badgeGrantedTitle: true,
            views: 1,
            profileBackgroundUploadId: 1,
            cardBackgroundUploadId: 1,
            grantedTitleBadgeId: 1,
            featuredTopicId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserProfiles', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            location: 'BBBBBB',
            website: 'BBBBBB',
            bioRaw: 'BBBBBB',
            bioCooked: 'BBBBBB',
            dismissedBannerKey: 1,
            bioCookedVersion: 1,
            badgeGrantedTitle: true,
            views: 1,
            profileBackgroundUploadId: 1,
            cardBackgroundUploadId: 1,
            grantedTitleBadgeId: 1,
            featuredTopicId: 1,
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

      it('should delete a UserProfiles', () => {
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
