import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ThemeFieldsService } from 'app/entities/theme-fields/theme-fields.service';
import { IThemeFields, ThemeFields } from 'app/shared/model/theme-fields.model';

describe('Service Tests', () => {
  describe('ThemeFields Service', () => {
    let injector: TestBed;
    let service: ThemeFieldsService;
    let httpMock: HttpTestingController;
    let elemDefault: IThemeFields;
    let expectedResult: IThemeFields | IThemeFields[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ThemeFieldsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ThemeFields(0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ThemeFields', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ThemeFields()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ThemeFields', () => {
        const returnedFromService = Object.assign(
          {
            themeId: 1,
            targetId: 1,
            name: 'BBBBBB',
            value: 'BBBBBB',
            valueBaked: 'BBBBBB',
            compilerVersion: 'BBBBBB',
            error: 'BBBBBB',
            uploadId: 1,
            typeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ThemeFields', () => {
        const returnedFromService = Object.assign(
          {
            themeId: 1,
            targetId: 1,
            name: 'BBBBBB',
            value: 'BBBBBB',
            valueBaked: 'BBBBBB',
            compilerVersion: 'BBBBBB',
            error: 'BBBBBB',
            uploadId: 1,
            typeId: 1,
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

      it('should delete a ThemeFields', () => {
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
