import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiscourseTestModule } from '../../../test.module';
import { CategoryTagStatsDetailComponent } from 'app/entities/category-tag-stats/category-tag-stats-detail.component';
import { CategoryTagStats } from 'app/shared/model/category-tag-stats.model';

describe('Component Tests', () => {
  describe('CategoryTagStats Management Detail Component', () => {
    let comp: CategoryTagStatsDetailComponent;
    let fixture: ComponentFixture<CategoryTagStatsDetailComponent>;
    const route = ({ data: of({ categoryTagStats: new CategoryTagStats(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiscourseTestModule],
        declarations: [CategoryTagStatsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CategoryTagStatsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoryTagStatsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categoryTagStats on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoryTagStats).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
