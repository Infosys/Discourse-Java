import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { NotificationsUpdateComponent } from 'app/entities/notifications/notifications-update.component';
import { NotificationsService } from 'app/entities/notifications/notifications.service';
import { Notifications } from 'app/shared/model/notifications.model';

describe('Component Tests', () => {
  describe('Notifications Management Update Component', () => {
    let comp: NotificationsUpdateComponent;
    let fixture: ComponentFixture<NotificationsUpdateComponent>;
    let service: NotificationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [NotificationsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NotificationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotificationsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotificationsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Notifications(123);
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
        const entity = new Notifications();
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
