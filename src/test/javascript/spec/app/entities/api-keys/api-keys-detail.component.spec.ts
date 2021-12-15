import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ApiKeysDetailComponent } from 'app/entities/api-keys/api-keys-detail.component';
import { ApiKeys } from 'app/shared/model/api-keys.model';

describe('Component Tests', () => {
  describe('ApiKeys Management Detail Component', () => {
    let comp: ApiKeysDetailComponent;
    let fixture: ComponentFixture<ApiKeysDetailComponent>;
    const route = ({ data: of({ apiKeys: new ApiKeys(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ApiKeysDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApiKeysDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApiKeysDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load apiKeys on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.apiKeys).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
