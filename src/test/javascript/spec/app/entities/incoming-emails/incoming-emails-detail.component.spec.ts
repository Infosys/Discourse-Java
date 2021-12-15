import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IncomingEmailsDetailComponent } from 'app/entities/incoming-emails/incoming-emails-detail.component';
import { IncomingEmails } from 'app/shared/model/incoming-emails.model';

describe('Component Tests', () => {
  describe('IncomingEmails Management Detail Component', () => {
    let comp: IncomingEmailsDetailComponent;
    let fixture: ComponentFixture<IncomingEmailsDetailComponent>;
    const route = ({ data: of({ incomingEmails: new IncomingEmails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IncomingEmailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncomingEmailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncomingEmailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incomingEmails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incomingEmails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
