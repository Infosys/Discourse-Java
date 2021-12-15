import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryFeaturedTopicsUpdateComponent } from 'app/entities/category-featured-topics/category-featured-topics-update.component';
import { CategoryFeaturedTopicsService } from 'app/entities/category-featured-topics/category-featured-topics.service';
import { CategoryFeaturedTopics } from 'app/shared/model/category-featured-topics.model';

describe('Component Tests', () => {
  describe('CategoryFeaturedTopics Management Update Component', () => {
    let comp: CategoryFeaturedTopicsUpdateComponent;
    let fixture: ComponentFixture<CategoryFeaturedTopicsUpdateComponent>;
    let service: CategoryFeaturedTopicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryFeaturedTopicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryFeaturedTopicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryFeaturedTopicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryFeaturedTopicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryFeaturedTopics(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryFeaturedTopics();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
