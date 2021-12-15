import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ApiKeyScopesDetailComponent } from 'app/entities/api-key-scopes/api-key-scopes-detail.component';
import { ApiKeyScopes } from 'app/shared/model/api-key-scopes.model';

describe('Component Tests', () => {
  describe('ApiKeyScopes Management Detail Component', () => {
    let comp: ApiKeyScopesDetailComponent;
    let fixture: ComponentFixture<ApiKeyScopesDetailComponent>;
    const route = ({ data: of({ apiKeyScopes: new ApiKeyScopes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ApiKeyScopesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApiKeyScopesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiKeyScopesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load apiKeyScopes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiKeyScopes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
