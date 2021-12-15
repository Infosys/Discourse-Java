import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoriesWebHooksDetailComponent } from 'app/entities/categories-web-hooks/categories-web-hooks-detail.component';
import { CategoriesWebHooks } from 'app/shared/model/categories-web-hooks.model';

describe('Component Tests', () => {
  describe('CategoriesWebHooks Management Detail Component', () => {
    let comp: CategoriesWebHooksDetailComponent;
    let fixture: ComponentFixture<CategoriesWebHooksDetailComponent>;
    const route = ({ data: of({ categoriesWebHooks: new CategoriesWebHooks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoriesWebHooksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoriesWebHooksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriesWebHooksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoriesWebHooks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoriesWebHooks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
