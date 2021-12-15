import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TranslationOverridesService } from 'app/entities/translation-overrides/translation-overrides.service';
import { ITranslationOverrides, TranslationOverrides } from 'app/shared/model/translation-overrides.model';

describe('Service Tests', () => {
  describe('TranslationOverrides Service', () => {
    let injector: TestBed;
    let service: TranslationOverridesService;
    let httpMock: HttpTestingController;
    let elemDefault: ITranslationOverrides;
    let expectedResult: ITranslationOverrides | ITranslationOverrides[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TranslationOverridesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TranslationOverrides(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TranslationOverrides', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TranslationOverrides()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TranslationOverrides', () => {
        const returnedFromService = Object.assign(
          {
            locale: 'BBBBBB',
            translationKey: 'BBBBBB',
            value: 'BBBBBB',
            compiledJs: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TranslationOverrides', () => {
        const returnedFromService = Object.assign(
          {
            locale: 'BBBBBB',
            translationKey: 'BBBBBB',
            value: 'BBBBBB',
            compiledJs: 'BBBBBB',
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

      it('should delete a TranslationOverrides', () => {
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
