import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmbeddableHostsService } from 'app/entities/embeddable-hosts/embeddable-hosts.service';
import { IEmbeddableHosts, EmbeddableHosts } from 'app/shared/model/embeddable-hosts.model';

describe('Service Tests', () => {
  describe('EmbeddableHosts Service', () => {
    let injector: TestBed;
    let service: EmbeddableHostsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmbeddableHosts;
    let expectedResult: IEmbeddableHosts | IEmbeddableHosts[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmbeddableHostsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EmbeddableHosts(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EmbeddableHosts', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EmbeddableHosts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EmbeddableHosts', () => {
        const returnedFromService = Object.assign(
          {
            host: 'BBBBBB',
            categoryId: 1,
            className: 'BBBBBB',
            allowedPaths: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EmbeddableHosts', () => {
        const returnedFromService = Object.assign(
          {
            host: 'BBBBBB',
            categoryId: 1,
            className: 'BBBBBB',
            allowedPaths: 'BBBBBB',
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

      it('should delete a EmbeddableHosts', () => {
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
