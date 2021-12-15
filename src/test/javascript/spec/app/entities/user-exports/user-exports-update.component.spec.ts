import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserExportsUpdateComponent } from 'app/entities/user-exports/user-exports-update.component';
import { UserExportsService } from 'app/entities/user-exports/user-exports.service';
import { UserExports } from 'app/shared/model/user-exports.model';

describe('Component Tests', () => {
  describe('UserExports Management Update Component', () => {
    let comp: UserExportsUpdateComponent;
    let fixture: ComponentFixture<UserExportsUpdateComponent>;
    let service: UserExportsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserExportsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserExportsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserExportsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserExportsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserExports(123);
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
        const entity = new UserExports();
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
