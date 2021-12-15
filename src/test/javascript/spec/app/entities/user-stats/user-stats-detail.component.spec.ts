import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { UserStatsDetailComponent } from 'app/entities/user-stats/user-stats-detail.component';
import { UserStats } from 'app/shared/model/user-stats.model';

describe('Component Tests', () => {
  describe('UserStats Management Detail Component', () => {
    let comp: UserStatsDetailComponent;
    let fixture: ComponentFixture<UserStatsDetailComponent>;
    const route = ({ data: of({ userStats: new UserStats(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [UserStatsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserStatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserStatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userStats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userStats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
