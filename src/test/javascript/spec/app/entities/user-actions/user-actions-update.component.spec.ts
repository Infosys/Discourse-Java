import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserActionsUpdateComponent } from 'app/entities/user-actions/user-actions-update.component';
import { UserActionsService } from 'app/entities/user-actions/user-actions.service';
import { UserActions } from 'app/shared/model/user-actions.model';

describe('Component Tests', () => {
  describe('UserActions Management Update Component', () => {
    let comp: UserActionsUpdateComponent;
    let fixture: ComponentFixture<UserActionsUpdateComponent>;
    let service: UserActionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserActionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserActionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserActionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserActionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserActions(123);
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
        const entity = new UserActions();
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
