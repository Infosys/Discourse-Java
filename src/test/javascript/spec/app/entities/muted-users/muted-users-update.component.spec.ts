import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { MutedUsersUpdateComponent } from 'app/entities/muted-users/muted-users-update.component';
import { MutedUsersService } from 'app/entities/muted-users/muted-users.service';
import { MutedUsers } from 'app/shared/model/muted-users.model';

describe('Component Tests', () => {
  describe('MutedUsers Management Update Component', () => {
    let comp: MutedUsersUpdateComponent;
    let fixture: ComponentFixture<MutedUsersUpdateComponent>;
    let service: MutedUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [MutedUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MutedUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MutedUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MutedUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MutedUsers(123);
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
        const entity = new MutedUsers();
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
