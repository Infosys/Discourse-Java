import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoriesWebHooksUpdateComponent } from 'app/entities/categories-web-hooks/categories-web-hooks-update.component';
import { CategoriesWebHooksService } from 'app/entities/categories-web-hooks/categories-web-hooks.service';
import { CategoriesWebHooks } from 'app/shared/model/categories-web-hooks.model';

describe('Component Tests', () => {
  describe('CategoriesWebHooks Management Update Component', () => {
    let comp: CategoriesWebHooksUpdateComponent;
    let fixture: ComponentFixture<CategoriesWebHooksUpdateComponent>;
    let service: CategoriesWebHooksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoriesWebHooksUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoriesWebHooksUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoriesWebHooksUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoriesWebHooksService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoriesWebHooks(123);
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
        const entity = new CategoriesWebHooks();
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
