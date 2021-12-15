import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmailLogsDetailComponent } from 'app/entities/email-logs/email-logs-detail.component';
import { EmailLogs } from 'app/shared/model/email-logs.model';

describe('Component Tests', () => {
  describe('EmailLogs Management Detail Component', () => {
    let comp: EmailLogsDetailComponent;
    let fixture: ComponentFixture<EmailLogsDetailComponent>;
    const route = ({ data: of({ emailLogs: new EmailLogs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmailLogsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmailLogsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailLogsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emailLogs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailLogs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
