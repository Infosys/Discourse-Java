import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserProfileViewsUpdateComponent } from 'app/entities/user-profile-views/user-profile-views-update.component';
import { UserProfileViewsService } from 'app/entities/user-profile-views/user-profile-views.service';
import { UserProfileViews } from 'app/shared/model/user-profile-views.model';

describe('Component Tests', () => {
  describe('UserProfileViews Management Update Component', () => {
    let comp: UserProfileViewsUpdateComponent;
    let fixture: ComponentFixture<UserProfileViewsUpdateComponent>;
    let service: UserProfileViewsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserProfileViewsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserProfileViewsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfileViewsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileViewsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserProfileViews(123);
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
        const entity = new UserProfileViews();
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
