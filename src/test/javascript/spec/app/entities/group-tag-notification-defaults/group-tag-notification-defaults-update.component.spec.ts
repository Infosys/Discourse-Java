import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupTagNotificationDefaultsUpdateComponent } from 'app/entities/group-tag-notification-defaults/group-tag-notification-defaults-update.component';
import { GroupTagNotificationDefaultsService } from 'app/entities/group-tag-notification-defaults/group-tag-notification-defaults.service';
import { GroupTagNotificationDefaults } from 'app/shared/model/group-tag-notification-defaults.model';

describe('Component Tests', () => {
  describe('GroupTagNotificationDefaults Management Update Component', () => {
    let comp: GroupTagNotificationDefaultsUpdateComponent;
    let fixture: ComponentFixture<GroupTagNotificationDefaultsUpdateComponent>;
    let service: GroupTagNotificationDefaultsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupTagNotificationDefaultsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupTagNotificationDefaultsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupTagNotificationDefaultsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupTagNotificationDefaultsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupTagNotificationDefaults(123);
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
        const entity = new GroupTagNotificationDefaults();
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
