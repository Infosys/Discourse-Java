import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupCustomFieldsUpdateComponent } from 'app/entities/group-custom-fields/group-custom-fields-update.component';
import { GroupCustomFieldsService } from 'app/entities/group-custom-fields/group-custom-fields.service';
import { GroupCustomFields } from 'app/shared/model/group-custom-fields.model';

describe('Component Tests', () => {
  describe('GroupCustomFields Management Update Component', () => {
    let comp: GroupCustomFieldsUpdateComponent;
    let fixture: ComponentFixture<GroupCustomFieldsUpdateComponent>;
    let service: GroupCustomFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupCustomFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupCustomFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupCustomFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupCustomFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupCustomFields(123);
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
        const entity = new GroupCustomFields();
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
