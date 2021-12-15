import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SchedulerStatsDetailComponent } from 'app/entities/scheduler-stats/scheduler-stats-detail.component';
import { SchedulerStats } from 'app/shared/model/scheduler-stats.model';

describe('Component Tests', () => {
  describe('SchedulerStats Management Detail Component', () => {
    let comp: SchedulerStatsDetailComponent;
    let fixture: ComponentFixture<SchedulerStatsDetailComponent>;
    const route = ({ data: of({ schedulerStats: new SchedulerStats(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SchedulerStatsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SchedulerStatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SchedulerStatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load schedulerStats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.schedulerStats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
