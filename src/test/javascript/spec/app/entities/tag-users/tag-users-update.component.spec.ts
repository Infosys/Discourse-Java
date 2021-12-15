import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { TagUsersUpdateComponent } from 'app/entities/tag-users/tag-users-update.component';
import { TagUsersService } from 'app/entities/tag-users/tag-users.service';
import { TagUsers } from 'app/shared/model/tag-users.model';

describe('Component Tests', () => {
  describe('TagUsers Management Update Component', () => {
    let comp: TagUsersUpdateComponent;
    let fixture: ComponentFixture<TagUsersUpdateComponent>;
    let service: TagUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [TagUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TagUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TagUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TagUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TagUsers(123);
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
        const entity = new TagUsers();
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
