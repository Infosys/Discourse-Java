import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserWarningsUpdateComponent } from 'app/entities/user-warnings/user-warnings-update.component';
import { UserWarningsService } from 'app/entities/user-warnings/user-warnings.service';
import { UserWarnings } from 'app/shared/model/user-warnings.model';

describe('Component Tests', () => {
  describe('UserWarnings Management Update Component', () => {
    let comp: UserWarningsUpdateComponent;
    let fixture: ComponentFixture<UserWarningsUpdateComponent>;
    let service: UserWarningsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserWarningsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserWarningsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserWarningsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserWarningsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserWarnings(123);
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
        const entity = new UserWarnings();
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
