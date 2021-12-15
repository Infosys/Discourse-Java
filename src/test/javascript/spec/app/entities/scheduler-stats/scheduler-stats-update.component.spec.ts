import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SchedulerStatsUpdateComponent } from 'app/entities/scheduler-stats/scheduler-stats-update.component';
import { SchedulerStatsService } from 'app/entities/scheduler-stats/scheduler-stats.service';
import { SchedulerStats } from 'app/shared/model/scheduler-stats.model';

describe('Component Tests', () => {
  describe('SchedulerStats Management Update Component', () => {
    let comp: SchedulerStatsUpdateComponent;
    let fixture: ComponentFixture<SchedulerStatsUpdateComponent>;
    let service: SchedulerStatsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchedulerStatsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SchedulerStatsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SchedulerStatsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SchedulerStatsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SchedulerStats(123);
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
        const entity = new SchedulerStats();
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
