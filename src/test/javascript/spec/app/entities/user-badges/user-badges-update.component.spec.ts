import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserBadgesUpdateComponent } from 'app/entities/user-badges/user-badges-update.component';
import { UserBadgesService } from 'app/entities/user-badges/user-badges.service';
import { UserBadges } from 'app/shared/model/user-badges.model';

describe('Component Tests', () => {
  describe('UserBadges Management Update Component', () => {
    let comp: UserBadgesUpdateComponent;
    let fixture: ComponentFixture<UserBadgesUpdateComponent>;
    let service: UserBadgesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserBadgesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserBadgesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserBadgesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserBadgesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserBadges(123);
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
        const entity = new UserBadges();
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
