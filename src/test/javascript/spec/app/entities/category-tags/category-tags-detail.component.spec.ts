import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryTagsDetailComponent } from 'app/entities/category-tags/category-tags-detail.component';
import { CategoryTags } from 'app/shared/model/category-tags.model';

describe('Component Tests', () => {
  describe('CategoryTags Management Detail Component', () => {
    let comp: CategoryTagsDetailComponent;
    let fixture: ComponentFixture<CategoryTagsDetailComponent>;
    const route = ({ data: of({ categoryTags: new CategoryTags(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryTagsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryTagsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryTagsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryTags on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryTags).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
