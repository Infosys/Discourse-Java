import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CategoriesService } from 'app/entities/categories/categories.service';
import { ICategories, Categories } from 'app/shared/model/categories.model';

describe('Service Tests', () => {
  describe('Categories Service', () => {
    let injector: TestBed;
    let service: CategoriesService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategories;
    let expectedResult: ICategories | ICategories[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CategoriesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Categories(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        false,
        0,
        0,
        false,
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        'AAAAAAA',
        false,
        0,
        0,
        false,
        false,
        false,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        false,
        0,
        false,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        false
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

      it('should create a Categories', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Categories()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Categories', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            color: 'BBBBBB',
            topicId: 1,
            topicCount: 1,
            userId: 'BBBBBB',
            topicsYear: 1,
            topicsMonth: 1,
            topicsWeek: 1,
            slug: 'BBBBBB',
            description: 'BBBBBB',
            textColor: 'BBBBBB',
            readRestricted: true,
            autoCloseHours: 1,
            postCount: 1,
            latestPostId: 1,
            latestTopicId: 1,
            position: 1,
            parentCategoryId: 1,
            postsYear: 1,
            postsMonth: 1,
            postsWeek: 1,
            emailIn: 'BBBBBB',
            emailInAllowStrangers: true,
            topicsDay: 1,
            postsDay: 1,
            allowBadges: true,
            nameLower: 'BBBBBB',
            autoCloseBasedOnLastPost: true,
            topicTemplate: 'BBBBBB',
            containsMessages: true,
            sortOrder: 'BBBBBB',
            sortAscending: true,
            uploadedLogoId: 1,
            uploadedBackgroundId: 1,
            topicFeaturedLinkAllowed: true,
            allTopicsWiki: true,
            showSubcategoryList: true,
            numFeaturedTopics: 1,
            defaultView: 'BBBBBB',
            subcategoryListStyle: 'BBBBBB',
            defaultTopPeriod: 'BBBBBB',
            mailinglistMirror: true,
            minimumRequiredTags: 1,
            navigateToFirstPostAfterRead: true,
            searchPriority: 1,
            allowGlobalTags: true,
            reviewableByGroupId: 1,
            requiredTagGroupId: 1,
            minTagsFromRequiredGroup: 1,
            readOnlyBanner: 'BBBBBB',
            defaultListFilter: 'BBBBBB',
            allowUnlimitedOwnerEditsOnFirstPost: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Categories', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            color: 'BBBBBB',
            topicId: 1,
            topicCount: 1,
            userId: 'BBBBBB',
            topicsYear: 1,
            topicsMonth: 1,
            topicsWeek: 1,
            slug: 'BBBBBB',
            description: 'BBBBBB',
            textColor: 'BBBBBB',
            readRestricted: true,
            autoCloseHours: 1,
            postCount: 1,
            latestPostId: 1,
            latestTopicId: 1,
            position: 1,
            parentCategoryId: 1,
            postsYear: 1,
            postsMonth: 1,
            postsWeek: 1,
            emailIn: 'BBBBBB',
            emailInAllowStrangers: true,
            topicsDay: 1,
            postsDay: 1,
            allowBadges: true,
            nameLower: 'BBBBBB',
            autoCloseBasedOnLastPost: true,
            topicTemplate: 'BBBBBB',
            containsMessages: true,
            sortOrder: 'BBBBBB',
            sortAscending: true,
            uploadedLogoId: 1,
            uploadedBackgroundId: 1,
            topicFeaturedLinkAllowed: true,
            allTopicsWiki: true,
            showSubcategoryList: true,
            numFeaturedTopics: 1,
            defaultView: 'BBBBBB',
            subcategoryListStyle: 'BBBBBB',
            defaultTopPeriod: 'BBBBBB',
            mailinglistMirror: true,
            minimumRequiredTags: 1,
            navigateToFirstPostAfterRead: true,
            searchPriority: 1,
            allowGlobalTags: true,
            reviewableByGroupId: 1,
            requiredTagGroupId: 1,
            minTagsFromRequiredGroup: 1,
            readOnlyBanner: 'BBBBBB',
            defaultListFilter: 'BBBBBB',
            allowUnlimitedOwnerEditsOnFirstPost: true,
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

      it('should delete a Categories', () => {
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
