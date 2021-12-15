import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ThemesService } from 'app/entities/themes/themes.service';
import { IThemes, Themes } from 'app/shared/model/themes.model';

describe('Service Tests', () => {
  describe('Themes Service', () => {
    let injector: TestBed;
    let service: ThemesService;
    let httpMock: HttpTestingController;
    let elemDefault: IThemes;
    let expectedResult: IThemes | IThemes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ThemesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Themes(0, 'AAAAAAA', 'AAAAAAA', 0, false, false, 0, 0, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Themes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Themes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Themes', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            userId: 'BBBBBB',
            compilerVersion: 1,
            userSelectable: true,
            hidden: true,
            colorSchemeId: 1,
            remoteThemeId: 1,
            componentAvailable: true,
            enabled: true,
            autoUpdate: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Themes', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            userId: 'BBBBBB',
            compilerVersion: 1,
            userSelectable: true,
            hidden: true,
            colorSchemeId: 1,
            remoteThemeId: 1,
            componentAvailable: true,
            enabled: true,
            autoUpdate: true,
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

      it('should delete a Themes', () => {
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
