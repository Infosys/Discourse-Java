import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ThemeModifierSetsService } from 'app/entities/theme-modifier-sets/theme-modifier-sets.service';
import { IThemeModifierSets, ThemeModifierSets } from 'app/shared/model/theme-modifier-sets.model';

describe('Service Tests', () => {
  describe('ThemeModifierSets Service', () => {
    let injector: TestBed;
    let service: ThemeModifierSetsService;
    let httpMock: HttpTestingController;
    let elemDefault: IThemeModifierSets;
    let expectedResult: IThemeModifierSets | IThemeModifierSets[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ThemeModifierSetsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ThemeModifierSets(0, 0, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ThemeModifierSets', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ThemeModifierSets()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ThemeModifierSets', () => {
        const returnedFromService = Object.assign(
          {
            themeId: 1,
            serializeTopicExcerpts: true,
            cspExtensions: 'BBBBBB',
            svgIcons: 'BBBBBB',
            topicThumbnailSizes: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ThemeModifierSets', () => {
        const returnedFromService = Object.assign(
          {
            themeId: 1,
            serializeTopicExcerpts: true,
            cspExtensions: 'BBBBBB',
            svgIcons: 'BBBBBB',
            topicThumbnailSizes: 'BBBBBB',
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

      it('should delete a ThemeModifierSets', () => {
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
