import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ColorSchemesService } from 'app/entities/color-schemes/color-schemes.service';
import { IColorSchemes, ColorSchemes } from 'app/shared/model/color-schemes.model';

describe('Service Tests', () => {
  describe('ColorSchemes Service', () => {
    let injector: TestBed;
    let service: ColorSchemesService;
    let httpMock: HttpTestingController;
    let elemDefault: IColorSchemes;
    let expectedResult: IColorSchemes | IColorSchemes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ColorSchemesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ColorSchemes(0, 'AAAAAAA', 0, false, 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ColorSchemes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ColorSchemes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ColorSchemes', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            version: 1,
            viaWizard: true,
            baseSchemeId: 'BBBBBB',
            themeId: 1,
            userSelectable: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ColorSchemes', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            version: 1,
            viaWizard: true,
            baseSchemeId: 'BBBBBB',
            themeId: 1,
            userSelectable: true,
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

      it('should delete a ColorSchemes', () => {
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
