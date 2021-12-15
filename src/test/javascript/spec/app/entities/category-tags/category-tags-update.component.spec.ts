import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryTagsUpdateComponent } from 'app/entities/category-tags/category-tags-update.component';
import { CategoryTagsService } from 'app/entities/category-tags/category-tags.service';
import { CategoryTags } from 'app/shared/model/category-tags.model';

describe('Component Tests', () => {
  describe('CategoryTags Management Update Component', () => {
    let comp: CategoryTagsUpdateComponent;
    let fixture: ComponentFixture<CategoryTagsUpdateComponent>;
    let service: CategoryTagsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryTagsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryTagsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryTagsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryTagsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryTags(123);
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
        const entity = new CategoryTags();
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
