import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RemoteThemesService } from 'app/entities/remote-themes/remote-themes.service';
import { IRemoteThemes, RemoteThemes } from 'app/shared/model/remote-themes.model';

describe('Service Tests', () => {
  describe('RemoteThemes Service', () => {
    let injector: TestBed;
    let service: RemoteThemesService;
    let httpMock: HttpTestingController;
    let elemDefault: IRemoteThemes;
    let expectedResult: IRemoteThemes | IRemoteThemes[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RemoteThemesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new RemoteThemes(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            remoteUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RemoteThemes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            remoteUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            remoteUpdatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new RemoteThemes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RemoteThemes', () => {
        const returnedFromService = Object.assign(
          {
            remoteUrl: 'BBBBBB',
            remoteVersion: 'BBBBBB',
            localVersion: 'BBBBBB',
            aboutUrl: 'BBBBBB',
            licenseUrl: 'BBBBBB',
            commitsBehind: 1,
            remoteUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            privateKey: 'BBBBBB',
            branch: 'BBBBBB',
            lastErrorText: 'BBBBBB',
            authors: 'BBBBBB',
            themeVersion: 'BBBBBB',
            minimumDiscourseVersion: 'BBBBBB',
            maximumDiscourseVersion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            remoteUpdatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RemoteThemes', () => {
        const returnedFromService = Object.assign(
          {
            remoteUrl: 'BBBBBB',
            remoteVersion: 'BBBBBB',
            localVersion: 'BBBBBB',
            aboutUrl: 'BBBBBB',
            licenseUrl: 'BBBBBB',
            commitsBehind: 1,
            remoteUpdatedAt: currentDate.format(DATE_TIME_FORMAT),
            privateKey: 'BBBBBB',
            branch: 'BBBBBB',
            lastErrorText: 'BBBBBB',
            authors: 'BBBBBB',
            themeVersion: 'BBBBBB',
            minimumDiscourseVersion: 'BBBBBB',
            maximumDiscourseVersion: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            remoteUpdatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a RemoteThemes', () => {
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
