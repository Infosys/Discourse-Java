import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { EmailTokensDetailComponent } from 'app/entities/email-tokens/email-tokens-detail.component';
import { EmailTokens } from 'app/shared/model/email-tokens.model';

describe('Component Tests', () => {
  describe('EmailTokens Management Detail Component', () => {
    let comp: EmailTokensDetailComponent;
    let fixture: ComponentFixture<EmailTokensDetailComponent>;
    const route = ({ data: of({ emailTokens: new EmailTokens(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [EmailTokensDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmailTokensDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmailTokensDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emailTokens on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emailTokens).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
