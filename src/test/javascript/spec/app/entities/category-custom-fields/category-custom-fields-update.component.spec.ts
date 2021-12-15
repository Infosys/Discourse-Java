import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryCustomFieldsUpdateComponent } from 'app/entities/category-custom-fields/category-custom-fields-update.component';
import { CategoryCustomFieldsService } from 'app/entities/category-custom-fields/category-custom-fields.service';
import { CategoryCustomFields } from 'app/shared/model/category-custom-fields.model';

describe('Component Tests', () => {
  describe('CategoryCustomFields Management Update Component', () => {
    let comp: CategoryCustomFieldsUpdateComponent;
    let fixture: ComponentFixture<CategoryCustomFieldsUpdateComponent>;
    let service: CategoryCustomFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryCustomFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryCustomFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryCustomFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryCustomFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryCustomFields(123);
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
        const entity = new CategoryCustomFields();
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
