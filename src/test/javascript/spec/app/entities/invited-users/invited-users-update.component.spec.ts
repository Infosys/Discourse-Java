import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { InvitedUsersUpdateComponent } from 'app/entities/invited-users/invited-users-update.component';
import { InvitedUsersService } from 'app/entities/invited-users/invited-users.service';
import { InvitedUsers } from 'app/shared/model/invited-users.model';

describe('Component Tests', () => {
  describe('InvitedUsers Management Update Component', () => {
    let comp: InvitedUsersUpdateComponent;
    let fixture: ComponentFixture<InvitedUsersUpdateComponent>;
    let service: InvitedUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [InvitedUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InvitedUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvitedUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitedUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvitedUsers(123);
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
        const entity = new InvitedUsers();
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
