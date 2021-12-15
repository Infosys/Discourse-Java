import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GroupsService } from 'app/entities/groups/groups.service';
import { IGroups, Groups } from 'app/shared/model/groups.model';

describe('Service Tests', () => {
  describe('Groups Service', () => {
    let injector: TestBed;
    let service: GroupsService;
    let httpMock: HttpTestingController;
    let elemDefault: IGroups;
    let expectedResult: IGroups | IGroups[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GroupsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Groups(
        0,
        'AAAAAAA',
        false,
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        0,
        0,
        false,
        false,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        0,
        false,
        'AAAAAAA',
        0,
        false,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        0,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Groups', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Groups()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Groups', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            automatic: true,
            userCount: 1,
            automaticMembershipEmailDomains: 'BBBBBB',
            primaryGroup: true,
            title: 'BBBBBB',
            grantTrustLevel: 1,
            incomingEmail: 'BBBBBB',
            hasMessages: true,
            flairUrl: 'BBBBBB',
            flairBgColor: 'BBBBBB',
            flairColor: 'BBBBBB',
            bioRaw: 'BBBBBB',
            bioCooked: 'BBBBBB',
            allowMembershipRequests: true,
            fullName: 'BBBBBB',
            defaultNotificationLevel: 1,
            visibilityLevel: 1,
            publicExit: true,
            publicAdmission: true,
            membershipRequestTemplate: 'BBBBBB',
            messageableLevel: 1,
            mentionableLevel: 1,
            smtpServer: 'BBBBBB',
            smtpPort: 1,
            smtpSsl: true,
            imapServer: 'BBBBBB',
            imapPort: 1,
            imapSsl: true,
            imapMailboxName: 'BBBBBB',
            imapUidValidity: 1,
            imapLastUid: 1,
            emailUsername: 'BBBBBB',
            emailPassword: 'BBBBBB',
            publishReadState: true,
            membersVisibilityLevel: 1,
            imapLastError: 'BBBBBB',
            imapOldEmails: 1,
            imapNewEmails: 1,
            flairIcon: 'BBBBBB',
            flairUploadId: 1,
            allowUnknownSenderTopicReplies: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Groups', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            automatic: true,
            userCount: 1,
            automaticMembershipEmailDomains: 'BBBBBB',
            primaryGroup: true,
            title: 'BBBBBB',
            grantTrustLevel: 1,
            incomingEmail: 'BBBBBB',
            hasMessages: true,
            flairUrl: 'BBBBBB',
            flairBgColor: 'BBBBBB',
            flairColor: 'BBBBBB',
            bioRaw: 'BBBBBB',
            bioCooked: 'BBBBBB',
            allowMembershipRequests: true,
            fullName: 'BBBBBB',
            defaultNotificationLevel: 1,
            visibilityLevel: 1,
            publicExit: true,
            publicAdmission: true,
            membershipRequestTemplate: 'BBBBBB',
            messageableLevel: 1,
            mentionableLevel: 1,
            smtpServer: 'BBBBBB',
            smtpPort: 1,
            smtpSsl: true,
            imapServer: 'BBBBBB',
            imapPort: 1,
            imapSsl: true,
            imapMailboxName: 'BBBBBB',
            imapUidValidity: 1,
            imapLastUid: 1,
            emailUsername: 'BBBBBB',
            emailPassword: 'BBBBBB',
            publishReadState: true,
            membersVisibilityLevel: 1,
            imapLastError: 'BBBBBB',
            imapOldEmails: 1,
            imapNewEmails: 1,
            flairIcon: 'BBBBBB',
            flairUploadId: 1,
            allowUnknownSenderTopicReplies: true,
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

      it('should delete a Groups', () => {
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
