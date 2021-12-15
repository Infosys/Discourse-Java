import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryFeaturedTopicsDetailComponent } from 'app/entities/category-featured-topics/category-featured-topics-detail.component';
import { CategoryFeaturedTopics } from 'app/shared/model/category-featured-topics.model';

describe('Component Tests', () => {
  describe('CategoryFeaturedTopics Management Detail Component', () => {
    let comp: CategoryFeaturedTopicsDetailComponent;
    let fixture: ComponentFixture<CategoryFeaturedTopicsDetailComponent>;
    const route = ({ data: of({ categoryFeaturedTopics: new CategoryFeaturedTopics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryFeaturedTopicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryFeaturedTopicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryFeaturedTopicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryFeaturedTopics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryFeaturedTopics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
