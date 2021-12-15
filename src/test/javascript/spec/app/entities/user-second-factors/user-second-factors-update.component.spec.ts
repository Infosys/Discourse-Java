import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserSecondFactorsUpdateComponent } from 'app/entities/user-second-factors/user-second-factors-update.component';
import { UserSecondFactorsService } from 'app/entities/user-second-factors/user-second-factors.service';
import { UserSecondFactors } from 'app/shared/model/user-second-factors.model';

describe('Component Tests', () => {
  describe('UserSecondFactors Management Update Component', () => {
    let comp: UserSecondFactorsUpdateComponent;
    let fixture: ComponentFixture<UserSecondFactorsUpdateComponent>;
    let service: UserSecondFactorsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserSecondFactorsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserSecondFactorsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserSecondFactorsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserSecondFactorsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserSecondFactors(123);
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
        const entity = new UserSecondFactors();
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
