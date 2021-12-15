import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserOptionsUpdateComponent } from 'app/entities/user-options/user-options-update.component';
import { UserOptionsService } from 'app/entities/user-options/user-options.service';
import { UserOptions } from 'app/shared/model/user-options.model';

describe('Component Tests', () => {
  describe('UserOptions Management Update Component', () => {
    let comp: UserOptionsUpdateComponent;
    let fixture: ComponentFixture<UserOptionsUpdateComponent>;
    let service: UserOptionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserOptionsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserOptionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserOptionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserOptionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserOptions(123);
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
        const entity = new UserOptions();
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
