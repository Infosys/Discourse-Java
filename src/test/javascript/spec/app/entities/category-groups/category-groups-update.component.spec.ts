import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryGroupsUpdateComponent } from 'app/entities/category-groups/category-groups-update.component';
import { CategoryGroupsService } from 'app/entities/category-groups/category-groups.service';
import { CategoryGroups } from 'app/shared/model/category-groups.model';

describe('Component Tests', () => {
  describe('CategoryGroups Management Update Component', () => {
    let comp: CategoryGroupsUpdateComponent;
    let fixture: ComponentFixture<CategoryGroupsUpdateComponent>;
    let service: CategoryGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryGroupsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryGroupsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryGroupsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryGroups(123);
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
        const entity = new CategoryGroups();
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
