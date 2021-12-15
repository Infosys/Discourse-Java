import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryUsersUpdateComponent } from 'app/entities/category-users/category-users-update.component';
import { CategoryUsersService } from 'app/entities/category-users/category-users.service';
import { CategoryUsers } from 'app/shared/model/category-users.model';

describe('Component Tests', () => {
  describe('CategoryUsers Management Update Component', () => {
    let comp: CategoryUsersUpdateComponent;
    let fixture: ComponentFixture<CategoryUsersUpdateComponent>;
    let service: CategoryUsersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryUsersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategoryUsersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryUsersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryUsersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategoryUsers(123);
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
        const entity = new CategoryUsers();
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
