import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmailChangeRequestsDetailComponent } from 'app/entities/email-change-requests/email-change-requests-detail.component';
import { EmailChangeRequests } from 'app/shared/model/email-change-requests.model';

describe('Component Tests', () => {
  describe('EmailChangeRequests Management Detail Component', () => {
    let comp: EmailChangeRequestsDetailComponent;
    let fixture: ComponentFixture<EmailChangeRequestsDetailComponent>;
    const route = ({ data: of({ emailChangeRequests: new EmailChangeRequests(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmailChangeRequestsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmailChangeRequestsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailChangeRequestsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emailChangeRequests on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailChangeRequests).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
