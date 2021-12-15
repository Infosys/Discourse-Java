import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserAvatarsUpdateComponent } from 'app/entities/user-avatars/user-avatars-update.component';
import { UserAvatarsService } from 'app/entities/user-avatars/user-avatars.service';
import { UserAvatars } from 'app/shared/model/user-avatars.model';

describe('Component Tests', () => {
  describe('UserAvatars Management Update Component', () => {
    let comp: UserAvatarsUpdateComponent;
    let fixture: ComponentFixture<UserAvatarsUpdateComponent>;
    let service: UserAvatarsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserAvatarsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAvatarsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAvatarsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAvatarsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAvatars(123);
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
        const entity = new UserAvatars();
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
