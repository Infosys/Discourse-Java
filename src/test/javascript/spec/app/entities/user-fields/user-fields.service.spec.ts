import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserFieldsService } from 'app/entities/user-fields/user-fields.service';
import { IUserFields, UserFields } from 'app/shared/model/user-fields.model';

describe('Service Tests', () => {
  describe('UserFields Service', () => {
    let injector: TestBed;
    let service: UserFieldsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserFields;
    let expectedResult: IUserFields | IUserFields[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserFieldsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserFields(0, 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', false, false, 0, false, 'AAAAAAA', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserFields', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserFields()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserFields', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            fieldType: 'BBBBBB',
            editable: true,
            description: 'BBBBBB',
            required: true,
            showOnProfile: true,
            position: 1,
            showOnUserCard: true,
            externalName: 'BBBBBB',
            externalType: 'BBBBBB',
            searchable: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserFields', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            fieldType: 'BBBBBB',
            editable: true,
            description: 'BBBBBB',
            required: true,
            showOnProfile: true,
            position: 1,
            showOnUserCard: true,
            externalName: 'BBBBBB',
            externalType: 'BBBBBB',
            searchable: true,
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

      it('should delete a UserFields', () => {
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
