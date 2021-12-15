import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TextClassificationService } from 'app/entities/text-classification/text-classification.service';
import { ITextClassification, TextClassification } from 'app/shared/model/text-classification.model';
import { TextClassficationType } from 'app/shared/model/enumerations/text-classfication-type.model';

describe('Service Tests', () => {
  describe('TextClassification Service', () => {
    let injector: TestBed;
    let service: TextClassificationService;
    let httpMock: HttpTestingController;
    let elemDefault: ITextClassification;
    let expectedResult: ITextClassification | ITextClassification[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TextClassificationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TextClassification(0, 0, 0, 0, 0, 0, 0, 0, TextClassficationType.TOPIC);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TextClassification', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TextClassification()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TextClassification', () => {
        const returnedFromService = Object.assign(
          {
            contentId: 1,
            toxicity: 1,
            severeToxicity: 1,
            obscene: 1,
            threat: 1,
            insult: 1,
            identityHate: 1,
            type: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TextClassification', () => {
        const returnedFromService = Object.assign(
          {
            contentId: 1,
            toxicity: 1,
            severeToxicity: 1,
            obscene: 1,
            threat: 1,
            insult: 1,
            identityHate: 1,
            type: 'BBBBBB',
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

      it('should delete a TextClassification', () => {
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
