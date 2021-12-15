import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagGroupPermissionsUpdateComponent } from 'app/entities/tag-group-permissions/tag-group-permissions-update.component';
import { TagGroupPermissionsService } from 'app/entities/tag-group-permissions/tag-group-permissions.service';
import { TagGroupPermissions } from 'app/shared/model/tag-group-permissions.model';

describe('Component Tests', () => {
  describe('TagGroupPermissions Management Update Component', () => {
    let comp: TagGroupPermissionsUpdateComponent;
    let fixture: ComponentFixture<TagGroupPermissionsUpdateComponent>;
    let service: TagGroupPermissionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagGroupPermissionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagGroupPermissionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagGroupPermissionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagGroupPermissionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagGroupPermissions(123);
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
        const entity = new TagGroupPermissions();
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
