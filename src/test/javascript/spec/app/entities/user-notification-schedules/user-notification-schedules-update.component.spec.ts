import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserNotificationSchedulesUpdateComponent } from 'app/entities/user-notification-schedules/user-notification-schedules-update.component';
import { UserNotificationSchedulesService } from 'app/entities/user-notification-schedules/user-notification-schedules.service';
import { UserNotificationSchedules } from 'app/shared/model/user-notification-schedules.model';

describe('Component Tests', () => {
  describe('UserNotificationSchedules Management Update Component', () => {
    let comp: UserNotificationSchedulesUpdateComponent;
    let fixture: ComponentFixture<UserNotificationSchedulesUpdateComponent>;
    let service: UserNotificationSchedulesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserNotificationSchedulesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserNotificationSchedulesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserNotificationSchedulesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserNotificationSchedulesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserNotificationSchedules(123);
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
        const entity = new UserNotificationSchedules();
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
