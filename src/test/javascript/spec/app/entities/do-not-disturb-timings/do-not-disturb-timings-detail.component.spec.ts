import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { DoNotDisturbTimingsDetailComponent } from 'app/entities/do-not-disturb-timings/do-not-disturb-timings-detail.component';
import { DoNotDisturbTimings } from 'app/shared/model/do-not-disturb-timings.model';

describe('Component Tests', () => {
  describe('DoNotDisturbTimings Management Detail Component', () => {
    let comp: DoNotDisturbTimingsDetailComponent;
    let fixture: ComponentFixture<DoNotDisturbTimingsDetailComponent>;
    const route = ({ data: of({ doNotDisturbTimings: new DoNotDisturbTimings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [DoNotDisturbTimingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DoNotDisturbTimingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DoNotDisturbTimingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load doNotDisturbTimings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.doNotDisturbTimings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
