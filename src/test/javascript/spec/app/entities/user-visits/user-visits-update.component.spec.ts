import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserVisitsUpdateComponent } from 'app/entities/user-visits/user-visits-update.component';
import { UserVisitsService } from 'app/entities/user-visits/user-visits.service';
import { UserVisits } from 'app/shared/model/user-visits.model';

describe('Component Tests', () => {
  describe('UserVisits Management Update Component', () => {
    let comp: UserVisitsUpdateComponent;
    let fixture: ComponentFixture<UserVisitsUpdateComponent>;
    let service: UserVisitsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserVisitsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserVisitsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserVisitsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserVisitsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserVisits(123);
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
        const entity = new UserVisits();
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
