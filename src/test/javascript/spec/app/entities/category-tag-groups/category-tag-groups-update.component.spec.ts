import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryTagGroupsUpdateComponent } from 'app/entities/category-tag-groups/category-tag-groups-update.component';
import { CategoryTagGroupsService } from 'app/entities/category-tag-groups/category-tag-groups.service';
import { CategoryTagGroups } from 'app/shared/model/category-tag-groups.model';

describe('Component Tests', () => {
  describe('CategoryTagGroups Management Update Component', () => {
    let comp: CategoryTagGroupsUpdateComponent;
    let fixture: ComponentFixture<CategoryTagGroupsUpdateComponent>;
    let service: CategoryTagGroupsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryTagGroupsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryTagGroupsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryTagGroupsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryTagGroupsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryTagGroups(123);
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
        const entity = new CategoryTagGroups();
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
