import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ScreenedEmailsDetailComponent } from 'app/entities/screened-emails/screened-emails-detail.component';
import { ScreenedEmails } from 'app/shared/model/screened-emails.model';

describe('Component Tests', () => {
  describe('ScreenedEmails Management Detail Component', () => {
    let comp: ScreenedEmailsDetailComponent;
    let fixture: ComponentFixture<ScreenedEmailsDetailComponent>;
    const route = ({ data: of({ screenedEmails: new ScreenedEmails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ScreenedEmailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ScreenedEmailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScreenedEmailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load screenedEmails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.screenedEmails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
