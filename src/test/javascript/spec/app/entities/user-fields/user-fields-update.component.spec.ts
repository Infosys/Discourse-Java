import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserFieldsUpdateComponent } from 'app/entities/user-fields/user-fields-update.component';
import { UserFieldsService } from 'app/entities/user-fields/user-fields.service';
import { UserFields } from 'app/shared/model/user-fields.model';

describe('Component Tests', () => {
  describe('UserFields Management Update Component', () => {
    let comp: UserFieldsUpdateComponent;
    let fixture: ComponentFixture<UserFieldsUpdateComponent>;
    let service: UserFieldsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserFieldsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserFieldsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserFieldsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFieldsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserFields(123);
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
        const entity = new UserFields();
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
