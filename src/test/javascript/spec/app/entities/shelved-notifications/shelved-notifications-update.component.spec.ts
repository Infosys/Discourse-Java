import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { ShelvedNotificationsUpdateComponent } from 'app/entities/shelved-notifications/shelved-notifications-update.component';
import { ShelvedNotificationsService } from 'app/entities/shelved-notifications/shelved-notifications.service';
import { ShelvedNotifications } from 'app/shared/model/shelved-notifications.model';

describe('Component Tests', () => {
  describe('ShelvedNotifications Management Update Component', () => {
    let comp: ShelvedNotificationsUpdateComponent;
    let fixture: ComponentFixture<ShelvedNotificationsUpdateComponent>;
    let service: ShelvedNotificationsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [ShelvedNotificationsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShelvedNotificationsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShelvedNotificationsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShelvedNotificationsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShelvedNotifications(123);
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
        const entity = new ShelvedNotifications();
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
