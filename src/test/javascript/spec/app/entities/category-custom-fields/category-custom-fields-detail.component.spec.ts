import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryCustomFieldsDetailComponent } from 'app/entities/category-custom-fields/category-custom-fields-detail.component';
import { CategoryCustomFields } from 'app/shared/model/category-custom-fields.model';

describe('Component Tests', () => {
  describe('CategoryCustomFields Management Detail Component', () => {
    let comp: CategoryCustomFieldsDetailComponent;
    let fixture: ComponentFixture<CategoryCustomFieldsDetailComponent>;
    const route = ({ data: of({ categoryCustomFields: new CategoryCustomFields(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryCustomFieldsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryCustomFieldsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryCustomFieldsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryCustomFields on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryCustomFields).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
