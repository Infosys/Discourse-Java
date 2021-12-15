import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryFeaturedTopicsComponent } from 'app/entities/category-featured-topics/category-featured-topics.component';
import { CategoryFeaturedTopicsService } from 'app/entities/category-featured-topics/category-featured-topics.service';
import { CategoryFeaturedTopics } from 'app/shared/model/category-featured-topics.model';

describe('Component Tests', () => {
  describe('CategoryFeaturedTopics Management Component', () => {
    let comp: CategoryFeaturedTopicsComponent;
    let fixture: ComponentFixture<CategoryFeaturedTopicsComponent>;
    let service: CategoryFeaturedTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryFeaturedTopicsComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(CategoryFeaturedTopicsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryFeaturedTopicsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryFeaturedTopicsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategoryFeaturedTopics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categoryFeaturedTopics && comp.categoryFeaturedTopics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategoryFeaturedTopics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categoryFeaturedTopics && comp.categoryFeaturedTopics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
