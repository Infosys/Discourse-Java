import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { SkippedEmailLogsDetailComponent } from 'app/entities/skipped-email-logs/skipped-email-logs-detail.component';
import { SkippedEmailLogs } from 'app/shared/model/skipped-email-logs.model';

describe('Component Tests', () => {
  describe('SkippedEmailLogs Management Detail Component', () => {
    let comp: SkippedEmailLogsDetailComponent;
    let fixture: ComponentFixture<SkippedEmailLogsDetailComponent>;
    const route = ({ data: of({ skippedEmailLogs: new SkippedEmailLogs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [SkippedEmailLogsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SkippedEmailLogsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkippedEmailLogsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load skippedEmailLogs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skippedEmailLogs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
