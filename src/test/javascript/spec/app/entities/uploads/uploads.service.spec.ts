import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UploadsService } from 'app/entities/uploads/uploads.service';
import { IUploads, Uploads } from 'app/shared/model/uploads.model';

describe('Service Tests', () => {
  describe('Uploads Service', () => {
    let injector: TestBed;
    let service: UploadsService;
    let httpMock: HttpTestingController;
    let elemDefault: IUploads;
    let expectedResult: IUploads | IUploads[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UploadsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Uploads(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        false,
        0,
        'AAAAAAA',
        false,
        false,
        0,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            securityLastChangedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Uploads', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            securityLastChangedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            securityLastChangedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Uploads()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Uploads', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            originalFilename: 'BBBBBB',
            filesize: 1,
            width: 1,
            height: 1,
            url: 'BBBBBB',
            sha1: 'BBBBBB',
            origin: 'BBBBBB',
            retainHours: 1,
            extension: 'BBBBBB',
            thumbnailWidth: 1,
            thumbnailHeight: 1,
            etag: 'BBBBBB',
            secure: true,
            accessControlPostId: 1,
            originalSha1: 'BBBBBB',
            animated: true,
            verified: true,
            verificationStatus: 1,
            securityLastChangedAt: currentDate.format(DATE_TIME_FORMAT),
            securityLastChangedReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            securityLastChangedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Uploads', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            originalFilename: 'BBBBBB',
            filesize: 1,
            width: 1,
            height: 1,
            url: 'BBBBBB',
            sha1: 'BBBBBB',
            origin: 'BBBBBB',
            retainHours: 1,
            extension: 'BBBBBB',
            thumbnailWidth: 1,
            thumbnailHeight: 1,
            etag: 'BBBBBB',
            secure: true,
            accessControlPostId: 1,
            originalSha1: 'BBBBBB',
            animated: true,
            verified: true,
            verificationStatus: 1,
            securityLastChangedAt: currentDate.format(DATE_TIME_FORMAT),
            securityLastChangedReason: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            securityLastChangedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Uploads', () => {
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
