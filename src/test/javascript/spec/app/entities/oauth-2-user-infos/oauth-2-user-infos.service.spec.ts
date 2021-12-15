import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Oauth2UserInfosService } from 'app/entities/oauth-2-user-infos/oauth-2-user-infos.service';
import { IOauth2UserInfos, Oauth2UserInfos } from 'app/shared/model/oauth-2-user-infos.model';

describe('Service Tests', () => {
  describe('Oauth2UserInfos Service', () => {
    let injector: TestBed;
    let service: Oauth2UserInfosService;
    let httpMock: HttpTestingController;
    let elemDefault: IOauth2UserInfos;
    let expectedResult: IOauth2UserInfos | IOauth2UserInfos[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(Oauth2UserInfosService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Oauth2UserInfos(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Oauth2UserInfos', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Oauth2UserInfos()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Oauth2UserInfos', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            uid: 'BBBBBB',
            provider: 'BBBBBB',
            email: 'BBBBBB',
            name: 'BBBBBB',
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Oauth2UserInfos', () => {
        const returnedFromService = Object.assign(
          {
            userId: 'BBBBBB',
            uid: 'BBBBBB',
            provider: 'BBBBBB',
            email: 'BBBBBB',
            name: 'BBBBBB',
            updatedAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            updatedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Oauth2UserInfos', () => {
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
