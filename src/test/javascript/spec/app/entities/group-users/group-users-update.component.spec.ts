import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { GroupUsersUpdateComponent } from 'app/entities/group-users/group-users-update.component';
import { GroupUsersService } from 'app/entities/group-users/group-users.service';
import { GroupUsers } from 'app/shared/model/group-users.model';

describe('Component Tests', () => {
  describe('GroupUsers Management Update Component', () => {
    let comp: GroupUsersUpdateComponent;
    let fixture: ComponentFixture<GroupUsersUpdateComponent>;
    let service: GroupUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [GroupUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupUsers(123);
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
        const entity = new GroupUsers();
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
