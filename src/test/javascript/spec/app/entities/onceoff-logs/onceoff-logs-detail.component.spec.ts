import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { OnceoffLogsDetailComponent } from 'app/entities/onceoff-logs/onceoff-logs-detail.component';
import { OnceoffLogs } from 'app/shared/model/onceoff-logs.model';

describe('Component Tests', () => {
  describe('OnceoffLogs Management Detail Component', () => {
    let comp: OnceoffLogsDetailComponent;
    let fixture: ComponentFixture<OnceoffLogsDetailComponent>;
    const route = ({ data: of({ onceoffLogs: new OnceoffLogs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [OnceoffLogsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OnceoffLogsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OnceoffLogsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load onceoffLogs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.onceoffLogs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
