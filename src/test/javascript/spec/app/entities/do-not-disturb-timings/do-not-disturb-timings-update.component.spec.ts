import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DoNotDisturbTimingsUpdateComponent } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings-update.component';
import { DoNotDisturbTimingsService } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings.service';
import { DoNotDisturbTimings } from 'app/shared/model/do-not-disturb-timings.model';

describe('Component Tests', () => {
  describe('DoNotDisturbTimings Management Update Component', () => {
    let comp: DoNotDisturbTimingsUpdateComponent;
    let fixture: ComponentFixture<DoNotDisturbTimingsUpdateComponent>;
    let service: DoNotDisturbTimingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DoNotDisturbTimingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DoNotDisturbTimingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DoNotDisturbTimingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DoNotDisturbTimingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DoNotDisturbTimings(123);
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
        const entity = new DoNotDisturbTimings();
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
