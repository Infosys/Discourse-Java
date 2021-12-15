import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAssociatedAccountsDetailComponent } from 'app/entities/user-associated-accounts/user-associated-accounts-detail.component';
import { UserAssociatedAccounts } from 'app/shared/model/user-associated-accounts.model';

describe('Component Tests', () => {
  describe('UserAssociatedAccounts Management Detail Component', () => {
    let comp: UserAssociatedAccountsDetailComponent;
    let fixture: ComponentFixture<UserAssociatedAccountsDetailComponent>;
    const route = ({ data: of({ userAssociatedAccounts: new UserAssociatedAccounts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAssociatedAccountsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAssociatedAccountsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAssociatedAccountsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAssociatedAccounts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAssociatedAccounts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
