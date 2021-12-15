import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserEmailsDetailComponent } from 'app/entities/user-emails/user-emails-detail.component';
import { UserEmails } from 'app/shared/model/user-emails.model';

describe('Component Tests', () => {
  describe('UserEmails Management Detail Component', () => {
    let comp: UserEmailsDetailComponent;
    let fixture: ComponentFixture<UserEmailsDetailComponent>;
    const route = ({ data: of({ userEmails: new UserEmails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserEmailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserEmailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserEmailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userEmails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userEmails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
