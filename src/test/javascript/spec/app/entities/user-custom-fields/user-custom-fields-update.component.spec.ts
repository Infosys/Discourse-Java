import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserCustomFieldsUpdateComponent } from 'app/entities/user-custom-fields/user-custom-fields-update.component';
import { UserCustomFieldsService } from 'app/entities/user-custom-fields/user-custom-fields.service';
import { UserCustomFields } from 'app/shared/model/user-custom-fields.model';

describe('Component Tests', () => {
  describe('UserCustomFields Management Update Component', () => {
    let comp: UserCustomFieldsUpdateComponent;
    let fixture: ComponentFixture<UserCustomFieldsUpdateComponent>;
    let service: UserCustomFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserCustomFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserCustomFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserCustomFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserCustomFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserCustomFields(123);
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
        const entity = new UserCustomFields();
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
