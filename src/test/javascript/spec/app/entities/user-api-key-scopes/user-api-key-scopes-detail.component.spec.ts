import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserApiKeyScopesDetailComponent } from 'app/entities/user-api-key-scopes/user-api-key-scopes-detail.component';
import { UserApiKeyScopes } from 'app/shared/model/user-api-key-scopes.model';

describe('Component Tests', () => {
  describe('UserApiKeyScopes Management Detail Component', () => {
    let comp: UserApiKeyScopesDetailComponent;
    let fixture: ComponentFixture<UserApiKeyScopesDetailComponent>;
    const route = ({ data: of({ userApiKeyScopes: new UserApiKeyScopes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserApiKeyScopesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserApiKeyScopesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserApiKeyScopesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userApiKeyScopes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userApiKeyScopes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
