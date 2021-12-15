import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ThemeTranslationOverridesService } from 'app/entities/theme-translation-overrides/theme-translation-overrides.service';
import { IThemeTranslationOverrides, ThemeTranslationOverrides } from 'app/shared/model/theme-translation-overrides.model';

describe('Service Tests', () => {
  describe('ThemeTranslationOverrides Service', () => {
    let injector: TestBed;
    let service: ThemeTranslationOverridesService;
    let httpMock: HttpTestingController;
    let elemDefault: IThemeTranslationOverrides;
    let expectedResult: IThemeTranslationOverrides | IThemeTranslationOverrides[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ThemeTranslationOverridesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ThemeTranslationOverrides(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ThemeTranslationOverrides', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ThemeTranslationOverrides()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ThemeTranslationOverrides', () => {
        const returnedFromService = Object.assign(
          {
            themeId: 1,
            locale: 'BBBBBB',
            translationKey: 'BBBBBB',
            value: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ThemeTranslationOverrides', () => {
        const returnedFromService = Object.assign(
          {
            themeId: 1,
            locale: 'BBBBBB',
            translationKey: 'BBBBBB',
            value: 'BBBBBB',
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

      it('should delete a ThemeTranslationOverrides', () => {
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
