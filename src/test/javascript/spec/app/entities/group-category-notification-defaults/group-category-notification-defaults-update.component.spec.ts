import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupCategoryNotificationDefaultsUpdateComponent } from 'app/entities/group-category-notification-defaults/group-category-notification-defaults-update.component';
import { GroupCategoryNotificationDefaultsService } from 'app/entities/group-category-notification-defaults/group-category-notification-defaults.service';
import { GroupCategoryNotificationDefaults } from 'app/shared/model/group-category-notification-defaults.model';

describe('Component Tests', () => {
  describe('GroupCategoryNotificationDefaults Management Update Component', () => {
    let comp: GroupCategoryNotificationDefaultsUpdateComponent;
    let fixture: ComponentFixture<GroupCategoryNotificationDefaultsUpdateComponent>;
    let service: GroupCategoryNotificationDefaultsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupCategoryNotificationDefaultsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupCategoryNotificationDefaultsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupCategoryNotificationDefaultsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupCategoryNotificationDefaultsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupCategoryNotificationDefaults(123);
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
        const entity = new GroupCategoryNotificationDefaults();
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
