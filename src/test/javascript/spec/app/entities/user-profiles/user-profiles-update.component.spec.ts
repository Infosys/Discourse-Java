import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserProfilesUpdateComponent } from 'app/entities/user-profiles/user-profiles-update.component';
import { UserProfilesService } from 'app/entities/user-profiles/user-profiles.service';
import { UserProfiles } from 'app/shared/model/user-profiles.model';

describe('Component Tests', () => {
  describe('UserProfiles Management Update Component', () => {
    let comp: UserProfilesUpdateComponent;
    let fixture: ComponentFixture<UserProfilesUpdateComponent>;
    let service: UserProfilesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserProfilesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserProfilesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfilesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfilesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserProfiles(123);
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
        const entity = new UserProfiles();
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
