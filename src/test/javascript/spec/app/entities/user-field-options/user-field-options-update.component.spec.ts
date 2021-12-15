import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserFieldOptionsUpdateComponent } from 'app/entities/user-field-options/user-field-options-update.component';
import { UserFieldOptionsService } from 'app/entities/user-field-options/user-field-options.service';
import { UserFieldOptions } from 'app/shared/model/user-field-options.model';

describe('Component Tests', () => {
  describe('UserFieldOptions Management Update Component', () => {
    let comp: UserFieldOptionsUpdateComponent;
    let fixture: ComponentFixture<UserFieldOptionsUpdateComponent>;
    let service: UserFieldOptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserFieldOptionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserFieldOptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserFieldOptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFieldOptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserFieldOptions(123);
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
        const entity = new UserFieldOptions();
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
