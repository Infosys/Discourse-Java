import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategorySearchDataDetailComponent } from 'app/entities/category-search-data/category-search-data-detail.component';
import { CategorySearchData } from 'app/shared/model/category-search-data.model';

describe('Component Tests', () => {
  describe('CategorySearchData Management Detail Component', () => {
    let comp: CategorySearchDataDetailComponent;
    let fixture: ComponentFixture<CategorySearchDataDetailComponent>;
    const route = ({ data: of({ categorySearchData: new CategorySearchData(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategorySearchDataDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategorySearchDataDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorySearchDataDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorySearchData on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorySearchData).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
