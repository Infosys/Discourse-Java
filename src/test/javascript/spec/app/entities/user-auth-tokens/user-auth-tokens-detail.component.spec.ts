import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAuthTokensDetailComponent } from 'app/entities/user-auth-tokens/user-auth-tokens-detail.component';
import { UserAuthTokens } from 'app/shared/model/user-auth-tokens.model';

describe('Component Tests', () => {
  describe('UserAuthTokens Management Detail Component', () => {
    let comp: UserAuthTokensDetailComponent;
    let fixture: ComponentFixture<UserAuthTokensDetailComponent>;
    const route = ({ data: of({ userAuthTokens: new UserAuthTokens(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAuthTokensDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAuthTokensDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAuthTokensDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAuthTokens on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAuthTokens).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
