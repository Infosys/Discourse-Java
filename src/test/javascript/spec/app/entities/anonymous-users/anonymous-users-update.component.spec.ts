import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { AnonymousUsersUpdateComponent } from 'app/entities/anonymous-users/anonymous-users-update.component';
import { AnonymousUsersService } from 'app/entities/anonymous-users/anonymous-users.service';
import { AnonymousUsers } from 'app/shared/model/anonymous-users.model';

describe('Component Tests', () => {
  describe('AnonymousUsers Management Update Component', () => {
    let comp: AnonymousUsersUpdateComponent;
    let fixture: ComponentFixture<AnonymousUsersUpdateComponent>;
    let service: AnonymousUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [AnonymousUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnonymousUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnonymousUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnonymousUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnonymousUsers(123);
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
        const entity = new AnonymousUsers();
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
