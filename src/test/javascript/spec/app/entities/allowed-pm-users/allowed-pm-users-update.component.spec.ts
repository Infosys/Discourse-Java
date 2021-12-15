import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { AllowedPmUsersUpdateComponent } from 'app/entities/allowed-pm-users/allowed-pm-users-update.component';
import { AllowedPmUsersService } from 'app/entities/allowed-pm-users/allowed-pm-users.service';
import { AllowedPmUsers } from 'app/shared/model/allowed-pm-users.model';

describe('Component Tests', () => {
  describe('AllowedPmUsers Management Update Component', () => {
    let comp: AllowedPmUsersUpdateComponent;
    let fixture: ComponentFixture<AllowedPmUsersUpdateComponent>;
    let service: AllowedPmUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [AllowedPmUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AllowedPmUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllowedPmUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllowedPmUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllowedPmUsers(123);
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
        const entity = new AllowedPmUsers();
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
