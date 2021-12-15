import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { IgnoredUsersUpdateComponent } from 'app/entities/ignored-users/ignored-users-update.component';
import { IgnoredUsersService } from 'app/entities/ignored-users/ignored-users.service';
import { IgnoredUsers } from 'app/shared/model/ignored-users.model';

describe('Component Tests', () => {
  describe('IgnoredUsers Management Update Component', () => {
    let comp: IgnoredUsersUpdateComponent;
    let fixture: ComponentFixture<IgnoredUsersUpdateComponent>;
    let service: IgnoredUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [IgnoredUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IgnoredUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IgnoredUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IgnoredUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IgnoredUsers(123);
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
        const entity = new IgnoredUsers();
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
