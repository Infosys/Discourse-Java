import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ColorSchemeColorsService } from 'app/entities/color-scheme-colors/color-scheme-colors.service';
import { IColorSchemeColors, ColorSchemeColors } from 'app/shared/model/color-scheme-colors.model';

describe('Service Tests', () => {
  describe('ColorSchemeColors Service', () => {
    let injector: TestBed;
    let service: ColorSchemeColorsService;
    let httpMock: HttpTestingController;
    let elemDefault: IColorSchemeColors;
    let expectedResult: IColorSchemeColors | IColorSchemeColors[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ColorSchemeColorsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ColorSchemeColors(0, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ColorSchemeColors', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ColorSchemeColors()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ColorSchemeColors', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            hex: 'BBBBBB',
            colorSchemeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ColorSchemeColors', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            hex: 'BBBBBB',
            colorSchemeId: 1,
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

      it('should delete a ColorSchemeColors', () => {
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
