import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PostActionTypesService } from 'app/entities/post-action-types/post-action-types.service';
import { IPostActionTypes, PostActionTypes } from 'app/shared/model/post-action-types.model';

describe('Service Tests', () => {
  describe('PostActionTypes Service', () => {
    let injector: TestBed;
    let service: PostActionTypesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPostActionTypes;
    let expectedResult: IPostActionTypes | IPostActionTypes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PostActionTypesService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PostActionTypes(0, 'AAAAAAA', false, 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PostActionTypes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PostActionTypes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PostActionTypes', () => {
        const returnedFromService = Object.assign(
          {
            nameKey: 'BBBBBB',
            isFlag: true,
            icon: 'BBBBBB',
            position: 1,
            scoreBonus: 1,
            reviewablePriority: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PostActionTypes', () => {
        const returnedFromService = Object.assign(
          {
            nameKey: 'BBBBBB',
            isFlag: true,
            icon: 'BBBBBB',
            position: 1,
            scoreBonus: 1,
            reviewablePriority: 1,
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

      it('should delete a PostActionTypes', () => {
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
